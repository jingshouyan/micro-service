package io.jing.client.util;

import com.google.common.collect.Maps;
import io.jing.base.bean.*;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.thrift.MicroService;
import io.jing.base.thrift.ReqBean;
import io.jing.base.thrift.RspBean;
import io.jing.base.thrift.TokenBean;
import io.jing.base.util.code.Code;
import io.jing.base.util.id.IdGen;
import io.jing.base.util.rsp.RspUtil;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.client.listener.ZkListener;
import io.jing.client.transport.Transport;
import io.jing.client.transport.TransportProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jingshouyan
 * @date 2018/4/17 23:16
 */
@Slf4j
public class ClientUtil {
    /**
     * 服务信息
     */
    private static final Map<String,List<ServiceInfo>> SERVICE_INFO_MAP = Maps.newConcurrentMap();
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);
    private static final long ZK_WAIT_TIMEOUT = 3000;

    public static Rsp call(Token token,Req req){
        try{
            return callRpc(token,req);
        }catch (MicroServiceException e){
            return RspUtil.error(e);
        }
    }

    private static Rsp callRpc(Token token, Req req){
        Transport transport = null;
        Rsp rsp;
        try{
            COUNT_DOWN_LATCH.await(ZK_WAIT_TIMEOUT,TimeUnit.MILLISECONDS);
            Trace trace = ThreadLocalUtil.getTrace();
            String traceId;
            if(trace.getTraceId()==null){
                traceId = IdGen.gen();
            }else{
                traceId = trace.newTraceId();
            }
            ServiceInfo serviceInfo = null;
            List<ServiceInfo> serviceInfoList = SERVICE_INFO_MAP.get(req.getService());
            if(serviceInfoList==null || serviceInfoList.isEmpty()){
                throw new MicroServiceException(Code.SERVICE_NOT_FUND);
            }
            if(req.getRouter()!=null){
                serviceInfo = serviceInfoList.stream()
                        .filter(info->req.getRouter().equals(info.key()))
                        .findFirst()
                        .orElseThrow(()->new MicroServiceException(Code.INSTANCE_NOT_FUND));

            }else{
                serviceInfo = getServiceInfo(serviceInfoList);
            }
            transport = TransportProvider.get(serviceInfo);
            TProtocol tProtocol = new TBinaryProtocol(transport.getTTransport());
            MicroService.Client client = new MicroService.Client(tProtocol);
            ReqBean reqBean = req.reqBean();
            TokenBean tokenBean = token.tokenBean();
            tokenBean.setTraceId(traceId);
            if(req.isOneWay()){
                client.send(tokenBean,reqBean);
                rsp = RspUtil.success();
            }else{
                RspBean rspBean = client.call(tokenBean,reqBean);
                rsp = new Rsp(rspBean);
            }
            TransportProvider.restore(transport);
            return rsp;
        }catch (MicroServiceException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            TransportProvider.invalid(transport);
            throw new MicroServiceException(Code.CLIENT_ERROR,e);
        }
    }

    private static final Map<String,AtomicInteger> ATOMIC_INTEGER_MAP = Maps.newConcurrentMap();
    private static final int INT_SPACE = 1000000;
    private static ServiceInfo getServiceInfo(List<ServiceInfo> serviceInfoList){
        String serviceName = serviceInfoList.get(0).getName();
        AtomicInteger acc = ATOMIC_INTEGER_MAP.computeIfAbsent(serviceName,key->new AtomicInteger(0));
        int i = acc.getAndIncrement();
        ServiceInfo serviceInfo = serviceInfoList.get(i%serviceInfoList.size());
        if(i>Integer.MAX_VALUE - INT_SPACE){
            acc.set(0);
        }
        return serviceInfo;
    }

    private static void add(ServiceInfo serviceInfo){
        SERVICE_INFO_MAP.compute(serviceInfo.getName(),(s, serviceInfos) -> {
            if(serviceInfos==null){
                serviceInfos = Lists.newArrayList(serviceInfo);
            }
            serviceInfos.add(serviceInfo);
            return serviceInfos;
        });
    }

    private static void remove(ServiceInfo serviceInfo){
        SERVICE_INFO_MAP.compute(serviceInfo.getName(),(s, serviceInfos) -> {
            if(serviceInfos!=null){
                serviceInfos.removeIf(info->info.key().equals(serviceInfo.key()));
            }
            return serviceInfos;
        });
        TransportProvider.close(serviceInfo);
    }

    private static void update(ServiceInfo serviceInfo){
        SERVICE_INFO_MAP.compute(serviceInfo.getName(),(s, serviceInfos) -> {
            if(serviceInfos!=null){
                serviceInfos.stream()
                        .filter(info->info.key().equals(serviceInfo.key()))
                        .forEach(info->info.update(serviceInfo));
            }
            return serviceInfos;
        });
    }

    static{
        ZkListener.register((type, serviceInfo) -> {
            switch (type){
                case NODE_ADDED:
                    add(serviceInfo);
                    break;
                case NODE_UPDATED:
                    update(serviceInfo);
                    break;
                case NODE_REMOVED:
                    remove(serviceInfo);
                    break;
                case INITIALIZED:
                    COUNT_DOWN_LATCH.countDown();
                    break;
                default:
            }
        });
    }
}
