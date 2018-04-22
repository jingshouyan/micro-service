package io.jing.base.util.thread;

import io.jing.base.bean.Token;
import io.jing.base.bean.Trace;
import io.jing.base.util.threadlocal.ThreadLocalUtil;

import java.util.concurrent.ExecutorService;

/**
 * @author jingshouyan
 * @date 2018/4/15 19:41
 */
public class ExecUtil {

    public static void exec(ExecutorService service ,Runnable runnable){
        Token token = ThreadLocalUtil.getToken().copy();
        Trace trace = ThreadLocalUtil.getTrace();
        service.execute(()->{
            ThreadLocalUtil.setTrace(trace);
            ThreadLocalUtil.setToken(token);
            runnable.run();
            ThreadLocalUtil.removeToken();
            ThreadLocalUtil.removeTrace();
        });
    }

}
