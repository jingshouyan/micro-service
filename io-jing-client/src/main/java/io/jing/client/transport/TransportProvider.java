package io.jing.client.transport;

import com.google.common.collect.Maps;
import io.jing.base.bean.ServiceInfo;
import io.jing.client.constant.ClientConstant;
import io.jing.client.pool.TransportPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Map;

/**
 * @author jingshouyan
 * @date 2018/4/18 11:22
 */
@Slf4j
public class TransportProvider implements ClientConstant {
    private static final Map<String,TransportPool> TRANSPORT_POOL_MAP = Maps.newConcurrentMap();

    private static final GenericObjectPoolConfig cfg = new GenericObjectPoolConfig();
    static{
        cfg.setMinIdle(TRANSPORT_POOL_MIN_IDLE);
        cfg.setMaxIdle(TRANSPORT_POOL_MAX_IDLE);
        cfg.setMaxTotal(TRANSPORT_POOL_MAX_TOTAL);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            log.info("client stop. clean transport pool");
            TRANSPORT_POOL_MAP.forEach((key,transport)->{
                transport.close();
            });
        }));
    }

    public static Transport get(ServiceInfo serviceInfo) throws Exception{
        return TRANSPORT_POOL_MAP.computeIfAbsent(serviceInfo.key(),key->new TransportPool(serviceInfo,cfg)).get();
    }

    public static void restore(Transport transport){
        TransportPool transportPool = TRANSPORT_POOL_MAP.get(transport.getKey());
        if(null != transportPool){
            transportPool.restore(transport);
        }
    }

    public static void invalid(Transport transport){
        if(null == transport) {
            return;
        }
        try{
        TransportPool transportPool = TRANSPORT_POOL_MAP.get(transport.getKey());
        if(null != transportPool){
            transportPool.invalid(transport);
        }
        }catch (Exception e){
            log.error("pool invalid object error",e);
        }
    }

    public static void close(ServiceInfo serviceInfo) {
        TransportPool transportPool = TRANSPORT_POOL_MAP.remove(serviceInfo.key());
        if(null != transportPool){
            transportPool.close();
        }
    }


}
