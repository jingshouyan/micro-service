package io.jing.server;

import io.jing.base.bean.ServiceInfo;
import io.jing.client.transport.Transport;
import io.jing.client.transport.TransportProvider;
import io.jing.server.acl.bean.ResourceBean;
import io.jing.server.thrift.ResourceT;
import io.jing.server.thrift.ResponseT;
import io.jing.server.thrift.acl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.openjdk.jmh.annotations.*;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@State(Scope.Benchmark)@Slf4j
public class ThriftTest {

    @Benchmark
    @BenchmarkMode({ Mode.Throughput, Mode.AverageTime, Mode.SampleTime })
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @SneakyThrows
    public List<ResourceBean> myResource() {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setHost("127.0.0.1");
        serviceInfo.setPort(9999);
        Transport transport = TransportProvider.get(serviceInfo);
        TProtocol tProtocol = new TBinaryProtocol(transport.getTTransport());
        acl.Client client = new acl.Client(tProtocol);
        ResponseT r = client.myResource();
        TransportProvider.restore(transport);
        log.info("result: {}",r);
        return r.getResources().stream().map(rt->{
            ResourceBean rb = new ResourceBean();
            BeanUtils.copyProperties(rt,rb);
            return rb;
        }).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        ThriftTest thriftTest = new ThriftTest();
        thriftTest.myResource();
    }
}
