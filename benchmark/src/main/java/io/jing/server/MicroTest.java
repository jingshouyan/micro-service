package io.jing.server;

import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.util.config.ConfigSettings;
import io.jing.client.util.ClientUtil;
import io.jing.server.acl.bean.ResourceBean;
import io.jing.server.crud.bean.R;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode({ Mode.Throughput, Mode.AverageTime, Mode.SampleTime })
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1,time = 10)
@Measurement(iterations = 3,time = 10)
@Threads(32)
@Fork(1)
public class MicroTest {


    @Benchmark
    public List<ResourceBean> myResource(){
        Token token = new Token();
        Req req = Req.builder().service("acl").method("myResource").param("{}").build();
        Rsp rsp = ClientUtil.call(token,req);
        return rsp.list(ResourceBean.class);
    }


//    public static void main(String[] args) {
//        MicroTest microTest = new MicroTest();
//        microTest.myResource();
//    }
}
