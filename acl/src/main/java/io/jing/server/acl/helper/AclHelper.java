package io.jing.server.acl.helper;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.eventbus.Subscribe;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class AclHelper {
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ResourceDao resourceDao;

    private final LoadingCache<String,Optional<UserRoleBean>> UR_CACHE = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(30,TimeUnit.SECONDS)
            .build(userId -> userRoleDao.find(userId));

    private final LoadingCache<Long,Optional<RoleBean>> ROLE_CACHE = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(30,TimeUnit.SECONDS)
            .build(roleId -> roleDao.find(roleId));

    private final LoadingCache<String,Optional<ResourceBean>> RESOURCE_CACHE = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(30,TimeUnit.SECONDS)
            .build(path -> {
                String[] strings = path.split("#");
                String method = strings[0];
                String uri = strings[1];
                List<Compare> compares = CompareUtil.newInstance()
                        .field("method").eq(method)
                        .field("uri").eq(uri)
                        .compares();
                List<ResourceBean> resources = resourceDao.query(compares);
                return resources.stream().findFirst();
            });

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
            }
        };

        DmlEventBus.getEventBus(DmlType.UPDATE)
                .register(obj);

        DmlEventBus.getEventBus(DmlType.DELETE)
                .register(obj);
    }


    public boolean canActive(String userId,String method,String uri){
        String key = rKey(method,uri);
        Optional<ResourceBean> optResource = RESOURCE_CACHE.get(key);
        // 没有注册的资源无法访问
        if(optResource == null
                || !optResource.isPresent()
                ){
            return false;
        }

        ResourceBean resource = optResource.get();
        // 非可用资源无法访问
        if(resource.getState() != AclConstant.STATE_ENABLE){
            return false;
        }
        boolean pub = resource.getPub() == null ? false : resource.getPub();
        // 公开资源,无需认证
        if(pub){
            return true;
        }

        Optional<UserRoleBean> optUr = UR_CACHE.get(userId);
        // 用户没有角色
        if(optUr == null || !optUr.isPresent()){
            return false;
        }
        UserRoleBean ur = optUr.get();
        // 角色列表为空
        if(ur.getRoleIds() == null || ur.getRoleIds().isEmpty()){
            return false;
        }
        for (long roleId : ur.getRoleIds()){
            Optional<RoleBean> optRole = ROLE_CACHE.get(roleId);
            if(optRole != null && optRole.isPresent()){
                RoleBean role = optRole.get();
                // 角色可用,并且角色拥有该资源
                if(role.getState() == AclConstant.STATE_ENABLE
                        && role.getResourceIds() != null
                        && role.getResourceIds().contains(resource.getId())
                        ){
                    return true;
                }
            }
        }

        return false;
    }

    private String rKey(String method,String uri){
        return method + "#" +uri;
    }
}
