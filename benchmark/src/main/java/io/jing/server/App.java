package io.jing.server;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.client.util.ClientUtil;
import io.jing.server.acl.bean.ResourceBean;
import io.jing.server.crud.bean.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class App {

    public static final int CONCURRENCY = 32;

    @Benchmark
    @BenchmarkMode({ Mode.Throughput, Mode.AverageTime, Mode.SampleTime })
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public ResourceBean getResourceById(){
        Token token = new Token();
        R r = new R();
        r.setType("single");
        r.setId(10001L);
        r.setBean("resource");
        Req req = Req.builder().service("acl").method("query").paramObj(r).build();
        Rsp rsp = ClientUtil.call(token,req);
        return rsp.get(ResourceBean.class);
    }

    public List<ResourceBean> all(){
        Token token = new Token();
        R r = new R();
        r.setType("list");
        r.setBean("resource");
        Req req = Req.builder().service("acl").method("query").paramObj(r).build();
        Rsp rsp = ClientUtil.call(token,req);
        return rsp.list(ResourceBean.class);
    }

    public static void main(String[] args) throws Exception {
        App client = new App();

        List<ResourceBean> resourceBeans = client.all();

        for (int i = 0; i < 100; i++) {
            try {
                System.out.println(client.getResourceById());
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
        Thread.sleep(3000);

        Options opt = new OptionsBuilder()//
                .include(App.class.getSimpleName())//
                .warmupIterations(3)//
                .warmupTime(TimeValue.seconds(10))//
                .measurementIterations(3)//
                .measurementTime(TimeValue.seconds(10))//
                .threads(CONCURRENCY)//
                .forks(1)//
                .build();

        new Runner(opt).run();
    }
}
