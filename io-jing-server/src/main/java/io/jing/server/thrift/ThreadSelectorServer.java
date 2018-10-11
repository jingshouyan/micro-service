package io.jing.server.thrift;

import io.jing.base.bean.ServiceInfo;
import io.jing.base.thrift.MicroService.Iface;
import io.jing.base.thrift.MicroService.Processor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * @author jingshouyan
 * @date 2018/4/16 11:06
 */
@Slf4j
public class ThreadSelectorServer {

    private static final String SELECTOR_THREADS = "thrift.selectorThreads";
    private static final String WORKER_THREADS = "thrift.workerThreads";

    public static TServer getServer(Iface service, ServiceInfo serviceInfo) {
        int port = serviceInfo.getPort();
        int cpuNum = Runtime.getRuntime().availableProcessors();
        int selectorThreads = cpuNum * 2;
        int workerThreads = cpuNum * 4;
        TServer server = null;
        try {
            log.info("thrift service starting...[port:{}]", port);
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
            //多线程半同步半异步
            TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
            TProcessor tprocessor = new Processor<>(service);
            tArgs.processor(tprocessor);
            tArgs.transportFactory(new TFramedTransport.Factory());
            //设置读的最大参数块 默认最大long，容易引起内存溢出，必须限制
            tArgs.maxReadBufferBytes = serviceInfo.getMaxReadBufferBytes();
            tArgs.selectorThreads(selectorThreads).workerThreads(workerThreads);
            //二进制协议
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            // 多线程半同步半异步的服务模型
            server = new TThreadedSelectorServer(tArgs);
            log.info("{} = {}", SELECTOR_THREADS, selectorThreads);
            log.info("{} = {}", WORKER_THREADS, workerThreads);
        } catch (Exception e) {
            log.error("thrift service start failed", e);
        }
        log.info("thrift service started.  [port:{}]", port);
        
        return server;
    }
}
