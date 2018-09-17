package io.jing.server.zk;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.jing.base.bean.ServiceInfo;
import io.jing.base.thrift.MicroService;
import io.jing.base.util.json.JsonUtil;
import io.jing.base.util.net.NetUtil;
import io.jing.server.constant.ServerConstant;
import io.jing.server.iface.ServiceLoad;
import io.jing.server.monitor.MonitorUtil;
import io.jing.server.thrift.ThreadSelectorServer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.retry.RetryForever;
import org.apache.thrift.server.TServer;
import org.apache.zookeeper.CreateMode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author jingshouyan
 * @date 2018/4/16 22:09
 */
@Slf4j
public class Register implements ServerConstant {

    private static final long UPDATE_DELAY = 600;
    public static final ServiceInfo SERVICE_INSTANCE = new ServiceInfo();

    private static final String host = NetUtil.getIp(THRIFT_SERVER_IP);
    private static final CuratorFramework client = CuratorFrameworkFactory
            .builder().connectString(ZK_ADDRESS).canBeReadOnly(true)
            .retryPolicy(new RetryForever(5000))
            .build();

    private static final ExecutorService executor = new ThreadPoolExecutor(1,
            1,0L,TimeUnit.MICROSECONDS,new SynchronousQueue<>(),
            new ThreadFactoryBuilder().setNameFormat("zk-register-%d").build(),
            new ThreadPoolExecutor.AbortPolicy()
    );
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    static {
        try{
            client.start();
        }catch (Exception e){
            log.error("client start error.",e);
        }
    }

    public static void run(ServiceLoad serviceLoad){
        System.setProperty("SERVER_NAME",THRIFT_SERVER_NAME);
        System.setProperty("LOG_ROOT_PATH",LOG_ROOT_PATH);
        System.setProperty("LOG_LEVEL",LOG_LEVEL);
        System.setProperty("LOG_REF",LOG_REF);
        int port = port();
        SERVICE_INSTANCE.setName(THRIFT_SERVER_NAME);
        SERVICE_INSTANCE.setVersion(THRIFT_SERVER_VERSION);
        SERVICE_INSTANCE.setHost(host);
        SERVICE_INSTANCE.setPort(port);
        SERVICE_INSTANCE.setStartAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        System.setProperty("SERVER_INSTANCE",SERVICE_INSTANCE.key());
        TServer tServer = threadPoolServer(serviceLoad.load(),SERVICE_INSTANCE);
        executor.submit(()->registerService(SERVICE_INSTANCE));
        tServer.serve();
    }


    private static void registerService(ServiceInfo info) {
        try{
            log.info("register zk starting...");
            String path = fullPath(info);
            createZkNode(info);
            log.info("serviceInstance:[{}]", info);

            TreeCache cache = new TreeCache(client, path);
            cache.getListenable().addListener((cf,event)->{
                log.info("TREE CACHE {},{}",event.getType(),event.toString());
                if(event.getType() == TreeCacheEvent.Type.NODE_REMOVED
                        && path.equals(event.getData().getPath())){
                    createZkNode(info);
                }
            });
            cache.start();

            service.scheduleAtFixedRate(()->updateService(info),UPDATE_DELAY,UPDATE_DELAY,TimeUnit.SECONDS);

            Runtime.getRuntime().addShutdownHook(new Thread(()->{
                try{
                    log.info("server stop...");
                    cache.close();
                    client.delete().forPath(path);
                    client.close();
                    log.info("delete zk node [{}] .",path);
                }catch (Exception e){
                    log.error("delete zk node [{}] error.",path,e);
                }
            }));

        }catch (Exception e){
            log.error("register zk error.",e);
            System.exit(-1);
        }
    }

    @SneakyThrows
    private static String createZkNode(ServiceInfo info){
        String path = fullPath(info);
        info.setMonitorInfo(MonitorUtil.monitor());
        String data = JsonUtil.toJsonString(info);
        String realPath = client.create().
                creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path, data.getBytes());
        log.info("create zk node :{},data:{}",path,data);
        return realPath;
    }

    private static String fullPath(ServiceInfo info){
        String servicePath = ZK_BASE_PATH + "/" + THRIFT_SERVER_NAME;
        String fullPath = servicePath + "/" + info.key();
        return fullPath;
    }

    public static void updateService(ServiceInfo info){
        try{
            String fullPath = fullPath(info);
            info.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            info.setMonitorInfo(MonitorUtil.monitor());
            String data = JsonUtil.toJsonString(info);
            client.setData().forPath(fullPath,data.getBytes());
        }catch (Exception e){
            log.error("update zk error",e);
        }
    }


    /**
     * @param info 服务信息
     * @return TServer
     */
    private static TServer threadPoolServer(MicroService.Iface microService,ServiceInfo info) {
        return ThreadSelectorServer.getServer(microService, info);
    }


    private static int port(){
        if(THRIFT_SERVER_PORT_MIN <= THRIFT_SERVER_PORT && THRIFT_SERVER_PORT <= THRIFT_SERVER_PORT_MAX){
            return THRIFT_SERVER_PORT;
        }
        while (true) {
            Random r = new Random();
            int port = r.nextInt(THRIFT_SERVER_PORT_MAX - THRIFT_SERVER_PORT_MIN) + THRIFT_SERVER_PORT_MIN;
            if (!NetUtil.isLocalPortUsing(port)) {
                return port;
            }
        }
    }
}
