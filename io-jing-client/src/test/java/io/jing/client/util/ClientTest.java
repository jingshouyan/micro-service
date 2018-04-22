package io.jing.client.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.jing.base.bean.Empty;
import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.util.id.IdGen;
import io.jing.base.util.thread.ExecUtil;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author jingshouyan
 * @date 2018/4/18 20:15
 */
@Slf4j
public class ClientTest {
    private static ExecutorService executor = new ThreadPoolExecutor(40,
            200,6000L,TimeUnit.MICROSECONDS,new LinkedBlockingDeque<>(100000),
            new ThreadFactoryBuilder().setNameFormat("client-%d").build(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    public static void main(String[] args) throws Exception{
        Empty empty = new Empty();
        Token token = Token.builder().ticket("123").userId("abc").build();
        Req req1 = Req.builder().service("user").method("MUser").paramObj(empty).build();
        Rsp rsp2 = ClientUtil.call(token,req1);
        int k = 100000;
        UserBean userBean = new UserBean();

        userBean.getAccountBeans().add(new AccountBean());
        userBean.getAccountBeans().add(new AccountBean());
        userBean.getAccountBeans().add(new AccountBean());
        CountDownLatch countDownLatch = new CountDownLatch(k);
        ThreadLocalUtil.setTraceId(IdGen.gen());
        long start = System.currentTimeMillis();
        IntStream.range(0,k).forEach(i->{
            ExecUtil.exec(executor,()->{
                Req req = Req.builder().service("user").method("MUser").paramObj(userBean).build();
                Rsp rsp = ClientUtil.call(token,req);
                log.info(rsp.toString());
                countDownLatch.countDown();
            });
        });
        countDownLatch.await();
        long end  = System.currentTimeMillis();
        System.out.println("cost: {}"+(end-start));
    }
}
