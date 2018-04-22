package io.jing.client.pool;

import io.jing.base.bean.ServiceInfo;
import io.jing.base.util.config.ConfigSettings;
import io.jing.client.transport.Transport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;


/**
 * @author jingshouyan
 * @date 2018/4/17 20:41
 */
@Slf4j
public class TransportPoolFactory extends BasePooledObjectFactory<Transport> implements PooledObjectFactory<Transport> {

    private ServiceInfo serviceInfo;

    public TransportPoolFactory(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    @Override
    public Transport create() {
        try {
            Transport transport = new Transport();
            TSocket socket = new TSocket(serviceInfo.getHost(), serviceInfo.getPort());
            socket.getSocket().setKeepAlive(true);
            socket.getSocket().setTcpNoDelay(true);
            socket.getSocket().setSoLinger(false, 0);
            socket.setTimeout(serviceInfo.getTimeout());
            TTransport tTransport = new TFramedTransport(socket,serviceInfo.getMaxReadBufferBytes());
            tTransport.open();
            if (log.isDebugEnabled()) {
                log.debug("client pool make object success.");
            }
            transport.setKey(serviceInfo.key());
            transport.setTTransport(tTransport);
            return transport;
        } catch (Exception e) {
            log.warn("client pool make object error.",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public PooledObject<Transport> wrap(Transport transport) {
        return new DefaultPooledObject<>(transport);
    }

    @Override
    public void destroyObject(PooledObject<Transport> p) throws Exception {
        Transport transport = p.getObject();
        if(transport != null && transport.isOpen()){
            transport.getTTransport().close();
        }
    }

    @Override
    public boolean validateObject(PooledObject<Transport> p) {
        Transport transport = p.getObject();
        if(transport != null && transport.isOpen()){
            return true;
        }
        return false;
    }
}
