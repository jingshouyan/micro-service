package io.jing.server.zk;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.jing.base.bean.ServiceInfo;
import io.jing.base.thrift.MicroService;
import io.jing.base.util.net.NetUtil;
import io.jing.server.constant.ServerConstant;
import io.jing.server.iface.ServiceLoad;
import io.jing.server.monitor.MonitorUtil;
import io.jing.server.thrift.ThreadSelectorServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.thrift.server.TServer;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author jingshouyan
 * @date 2018/4/16 22:09
 */
@Component@Slf4j
public class Register implements ServerConstant {

    private static final long UPDATE_DELAY = 60;
    public static final ServiceInfo SERVICE_INSTANCE = new ServiceInfo();

    private static final String host = NetUtil.getIp(THRIFT_SERVER_IP);
    private static final CuratorFramework client = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));

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
            String fullPath = fullPath(info);
            info.setMonitorInfo(MonitorUtil.monitor());
            String data = JSON.toJSONString(info);
            String realPath = client.create().
                    creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(fullPath, data.getBytes());
            log.info("register zk path:[{}] data:[{}]", realPath, data);
            log.info("serviceInstance:[{}]", SERVICE_INSTANCE);


            service.scheduleAtFixedRate(()->updateService(info),UPDATE_DELAY,UPDATE_DELAY,TimeUnit.SECONDS);

            Runtime.getRuntime().addShutdownHook(new Thread(()->{
                try{
                    log.info("server stop...");
                    client.delete().forPath(realPath);
                    client.close();
                    log.info("delete zk node [{}] .",realPath);
                }catch (Exception e){
                    log.error("delete zk node [{}] error.",realPath,e);
                }
            }));

        }catch (Exception e){
            log.error("register zk error.",e);
            System.exit(-1);
        }
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
            String data = JSON.toJSONString(info);
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
