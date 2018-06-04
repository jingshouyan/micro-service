package io.jing.server.message.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import io.jing.server.message.bean.WsConnBean;
import io.jing.server.message.dao.WsConnDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author jingshouyan
 * #date 2018/6/4 17:23
 */
@Component
public class WsConnCache {

    @Autowired
    private WsConnDao wsConnDao;

    private Cache<String,List<WsConnBean>> wsConns = Caffeine.newBuilder()
            .expireAfterWrite(2,TimeUnit.MINUTES)
            .build();

    public void removeByUserId(String userId){
        wsConns.invalidate(userId);
    }

    public List<WsConnBean> getByUserIds(List<String> userIds){
        List<WsConnBean> wsConnBeanList = Lists.newArrayList();
        List<String> noCacheUserIds = Lists.newArrayList();
        for (String userId : userIds){
            List<WsConnBean> wsConnBeanListCache = wsConns.getIfPresent(userId);
            if(wsConnBeanListCache==null){
                noCacheUserIds.add(userId);
            }else{
                wsConnBeanList.addAll(wsConnBeanListCache);
            }
        }
        if(!noCacheUserIds.isEmpty()){
            List<WsConnBean> wsConnBeanListDb = wsConnDao.listByUserIdList(noCacheUserIds);
            wsConnBeanList.addAll(wsConnBeanListDb);
            Map<String,List<WsConnBean>> map = wsConnBeanListDb.stream()
                    .collect(Collectors.groupingBy(WsConnBean::getUserId));
            for (String userId : noCacheUserIds){
                List<WsConnBean> wsConnBeans = map.getOrDefault(userId,Lists.newArrayList());
                wsConns.put(userId,wsConnBeans);
            }
        }
        return wsConnBeanList;
    }

}
