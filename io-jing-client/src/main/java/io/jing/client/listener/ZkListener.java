package io.jing.client.listener;

import com.alibaba.fastjson.JSON;
import io.jing.base.bean.ServiceInfo;
import io.jing.client.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.retry.RetryNTimes;

import java.util.function.BiConsumer;

/**
 * @author jingshouyan
 * @date 2018/4/17 22:13
 */
@Slf4j
public class ZkListener implements Constant {

    private static CuratorFramework client= CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));

    public static void register(BiConsumer<TreeCacheEvent.Type,ServiceInfo> biConsumer) {
        try{
            client.start();
            TreeCache cache = new TreeCache(client, ZK_BASE_PATH);
            cache.start();
            cache.getListenable().addListener((client, event) -> {
                try {
                    String type = event.getType().name();
                    String path = null;
                    String data = null;
                    ChildData childData = event.getData();
                    if (childData != null) {
                        path = childData.getPath();
                        data = byte2String(childData.getData());
                    }
                    ServiceInfo info = toInfo(data);
                    log.info("tree changed type:[{}] path:[{}] data:[{}]", type, path, data);
                    if (null != info || event.getType() == TreeCacheEvent.Type.INITIALIZED || event.getType() == TreeCacheEvent.Type.NODE_REMOVED) {
                        if (event.getType() == TreeCacheEvent.Type.NODE_REMOVED && null == info) {
                            info = new ServiceInfo();
                            String[] strings = path.split("/");
                            String key = strings[strings.length - 1];
                            String name = strings[strings.length - 2];
                            info.key(key);
                            info.setName(name);
                        }
                        biConsumer.accept(event.getType(), info);
                    }
                }catch (Exception e){
                    log.error("zk listener error.",e);
                }
            });
        }catch (Exception e){
            log.error("zk client error.",e);
            throw new RuntimeException(e);
        }

    }

    private static ServiceInfo toInfo(String data) {
        ServiceInfo info = null;
        if (null != data && !"".equals(data)) {
            try {
                info = JSON.parseObject(data, ServiceInfo.class);
            } catch (Exception e) {
                log.warn("data:[{}] convert to ServiceInfo error", data, e);
            }
        }

        return info;
    }

    private static String byte2String(byte[] b) {
        if (b == null){
            return null;
        }
        return new String(b);
    }
}
