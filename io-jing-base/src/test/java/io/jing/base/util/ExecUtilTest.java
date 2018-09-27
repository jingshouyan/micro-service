package io.jing.base.util;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.jing.base.bean.Token;
import io.jing.base.bean.Trace;
import io.jing.base.util.thread.ExecUtil;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ExecUtilTest {

    private static final ExecutorService exec = new ThreadPoolExecutor(1, 1,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("related-pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy()
    );


    public static void main(String[] args) throws Exception {
        Token token = new Token();
        token.setUserId("aaa");
        token.setTicket("bbb");
        token.setClientType(0);
        ThreadLocalUtil.setToken(token);
        Trace trace = new Trace();
        trace.setTraceId("abc");
        trace.setNum(new AtomicInteger());
        ThreadLocalUtil.setTrace(trace);
        for (int i = 0; i < 100; i++) {
            ExecUtil.exec(exec,
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                Trace trace1 = ThreadLocalUtil.getTrace();
                                trace1.newTraceId();
                                log.info("token: {}", ThreadLocalUtil.getToken());
                                log.info("trace: {}", ThreadLocalUtil.getTrace());

                            } catch (Exception e) {
                            }
                        }
                    });
        }
        ThreadLocalUtil.removeTrace();
        ThreadLocalUtil.removeToken();
        Thread.sleep(1000000);



    }

}
