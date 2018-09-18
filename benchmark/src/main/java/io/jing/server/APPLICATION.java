package io.jing.server;

import io.jing.base.util.config.ConfigSettings;
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
public class APPLICATION {

    private static final int CONCURRENCY = ConfigSettings.get("threads").map(Integer::parseInt).orElse(8);

//    @Benchmark
//    @BenchmarkMode({ Mode.Throughput, Mode.AverageTime, Mode.SampleTime })
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
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
    @Benchmark
    @BenchmarkMode({ Mode.Throughput, Mode.AverageTime, Mode.SampleTime })
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<ResourceBean> myResource(){
        Token token = new Token();
        Req req = Req.builder().service("acl").method("myResource").param("{}").build();
        Rsp rsp = ClientUtil.call(token,req);
        return rsp.list(ResourceBean.class);
    }

    public static void main(String[] args) throws Exception {
        APPLICATION client = new APPLICATION();

        client.myResource();

        Thread.sleep(3000);

        Options opt = new OptionsBuilder()//
                .include(APPLICATION.class.getSimpleName())//
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
