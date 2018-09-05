package io.jing.server.acl.helper;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.code.Code;
import io.jing.client.util.ClientUtil;
import io.jing.server.acl.bean.ResourceBean;
import io.jing.server.acl.bean.RoleBean;
import io.jing.server.acl.bean.UserRoleBean;
import io.jing.server.acl.constant.AclConstant;
import io.jing.server.acl.dao.ResourceDao;
import io.jing.server.acl.dao.RoleDao;
import io.jing.server.acl.dao.UserRoleDao;
import io.jing.util.jdbc.core.event.DmlEventBus;
import io.jing.util.jdbc.core.event.DmlType;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class AclHelper implements AclConstant{
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ResourceDao resourceDao;

    private final LoadingCache<String,Optional<UserRoleBean>> UR_CACHE = Caffeine.newBuilder()
            .maximumSize(CACHE_SIZE)
            .expireAfterAccess(CACHE_HOLD_SECOND,TimeUnit.SECONDS)
            .build(
                    userId -> userRoleDao.find(userId)
                    .filter(ur -> ur.getRoleIds()!=null && !ur.getRoleIds().isEmpty())
            );

    private final LoadingCache<Long,Optional<RoleBean>> ROLE_CACHE = Caffeine.newBuilder()
            .maximumSize(CACHE_SIZE)
            .expireAfterAccess(CACHE_HOLD_SECOND,TimeUnit.SECONDS)
            .build(
                    roleId -> roleDao.find(roleId)
                            .filter(r -> STATE_ENABLE.equals(r.getState()))
                            .filter(r -> r.getResourceIds() != null && !r.getResourceIds().isEmpty())
            );

    private final LoadingCache<String,Optional<ResourceBean>> RESOURCE_CACHE = Caffeine.newBuilder()
            .maximumSize(CACHE_SIZE)
            .expireAfterAccess(CACHE_HOLD_SECOND,TimeUnit.SECONDS)
            .build(path -> {
                String[] strings = path.split("#");
                String method = strings[0];
                String uri = strings[1];
                List<Compare> compares = CompareUtil.newInstance()
                        .field("method").eq(method)
                        .field("uri").eq(uri)
                        .field("type").empty(false)
                        .field("state").eq(STATE_ENABLE)
                        .compares();
                List<ResourceBean> resources = resourceDao.query(compares);
                return resources.stream().findFirst();
            });

    private  final LoadingCache<String,Token> TOKEN_CACHE = Caffeine.newBuilder()
            .maximumSize(CACHE_SIZE)
            .expireAfterAccess(CACHE_HOLD_SECOND,TimeUnit.SECONDS)
            .build(ticket->{
                Token token = new Token();
                token.setTicket(ticket);
                Req req = Req.builder().service("user").method("getToken").param("{}").build();
                Rsp rsp = ClientUtil.call(token,req);
                if(rsp.getCode()!=Code.SUCCESS){
                    throw new MicroServiceException(rsp.getCode(),"user:"+rsp.getMessage());
                }
                token = rsp.get(Token.class);
                return token;
            });

    @Getter
    private List<ResourceBean> resources = Lists.newArrayList();

    private void loadResource(){
        List<Compare> compares = CompareUtil.newInstance()
                .field("state").eq(STATE_ENABLE)
                .field("type").empty(false)
                .compares();
        resources = resourceDao.query(compares);
    }

    @PostConstruct
    public void init(){
        Object obj = new Object(){
            @Subscribe
            public void userRole(UserRoleBean userRole){
                UR_CACHE.invalidate(userRole.getUserId());
            }

            @Subscribe
            public void role(RoleBean role){
                ROLE_CACHE.invalidate(role.getId());
            }

            @Subscribe
            public void resource(ResourceBean resource){
                if (resource.getMethod() == null || resource.getUri() == null){
                    //不知道改了啥,清除全部缓存
                    RESOURCE_CACHE.invalidateAll();
                } else {
                    String key = resource.getMethod() + "#" + resource.getUri();
                    RESOURCE_CACHE.invalidate(key);
                }
                loadResource();
            }
        };

        DmlEventBus.getEventBus(DmlType.CREATE)
                .register(obj);

        DmlEventBus.getEventBus(DmlType.UPDATE)
                .register(obj);

        DmlEventBus.getEventBus(DmlType.DELETE)
                .register(obj);
        loadResource();
    }



    public Token getToken(String ticket){
        return TOKEN_CACHE.get(ticket);
    }

    public void removeToken(Token token){
        TOKEN_CACHE.invalidate(token.getTicket());
        if(token.valid()){
            Req req = Req.builder().service("user").method("logout").param("{}").build();
            ClientUtil.call(token,req);
        }
    }

    public Optional<ResourceBean> getResourceOpt(String method, String uri) {
        String key = rKey(method,uri);
        return RESOURCE_CACHE.get(key);
    }

    public Optional<UserRoleBean> getUserRoleOpt(String userId){
        return UR_CACHE.get(userId);
    }

    public Optional<RoleBean> getRoleOpt(long roleId){
        return ROLE_CACHE.get(roleId);
    }


    public boolean canActive(String userId,long resourceId) {
        //用户角色
        return getUserRoleOpt(userId)
                // 角色Id
                .map(UserRoleBean::getRoleIds)
                // 转换资源
                .flatMap(roleIds ->
                        // 获取资源Id 列表
                        roleIds.stream().map(roleId->getRoleOpt(roleId).orElse(null))
                                .filter(Objects::nonNull)
                                //合并
                                .flatMap(role -> role.getResourceIds().stream())
                                //过滤
                                .filter(id -> id == resourceId || id == ALL_RESOURCE_ID)
                                .findFirst()
                )
                .isPresent();
    }

    private String rKey(String method,String uri){
        return method + "#" +uri;
    }
}
