package io.jing.server;

import com.alibaba.fastjson.JSON;
import io.jing.base.bean.ServiceInfo;
import io.jing.server.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.retry.RetryNTimes;

/**
 * @author jingshouyan
 * @date 2018/4/17 22:13
 */
@Slf4j
public class ZkListener implements Constant {

    private static final CuratorFramework client = CuratorFrameworkFactory.
            newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));

    public static void main(String[] args) throws Exception{
        init("/vrv");
        Thread.sleep(111111);
    }

    private static void init(String basePath) throws Exception {
        client.start();
        TreeCache cache = new TreeCache(client, basePath);
        cache.start();
        cache.getListenable().addListener((client, event) -> {
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
            switch (event.getType()) {
                case NODE_ADDED:
                    break;
                case NODE_UPDATED:
                    break;
                case NODE_REMOVED:
                    break;
                case INITIALIZED:
                    break;
                default:
                    break;
            }
        });
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
