package io.jing.server;

import io.jing.base.util.config.ConfigSettings;
import lombok.SneakyThrows;
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


    public static void main(String[] args) throws Exception {
        micro();
//        thrift();
    }

    @SneakyThrows
    public static void micro() {
        MicroTest client = new MicroTest();

        client.myResource();

        Thread.sleep(3000);

        Options opt = new OptionsBuilder()
                .include(MicroTest.class.getSimpleName())
                .warmupIterations(3)
                .warmupTime(TimeValue.seconds(10))
                .measurementIterations(3)
                .measurementTime(TimeValue.seconds(10))
                .threads(CONCURRENCY)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @SneakyThrows
    public static void thrift(){
        ThriftTest client = new ThriftTest();

        client.myResource();

        Thread.sleep(3000);

        Options opt = new OptionsBuilder()
                .include(ThriftTest.class.getSimpleName())
                .warmupIterations(3)
                .warmupTime(TimeValue.seconds(10))
                .measurementIterations(3)
                .measurementTime(TimeValue.seconds(10))
                .threads(CONCURRENCY)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
