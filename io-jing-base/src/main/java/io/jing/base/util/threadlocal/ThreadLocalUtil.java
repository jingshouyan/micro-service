package io.jing.base.util.threadlocal;

import io.jing.base.bean.Token;
import io.jing.base.bean.Trace;
import io.jing.base.constant.BaseConstant;
import org.slf4j.MDC;

import java.util.Optional;

/**
 * @author jingshouyan
 * @date 2018/4/15 0:16
 */
public class ThreadLocalUtil {

    private static final ThreadLocal<Token> TOKEN_THREAD_LOCAL = ThreadLocal.withInitial(Token::new);
    private static final ThreadLocal<Trace> TRACE_THREAD_LOCAL = ThreadLocal.withInitial(Trace::new);
    public static void setTraceId(String traceId){
        Trace trace = new Trace();
        trace.setTraceId(traceId);
        setTrace(trace);
    }

    public static void setTrace(Trace trace){
        TRACE_THREAD_LOCAL.set(trace);
        MDC.put(BaseConstant.TRACE_ID,trace.getTraceId());
    }

    public static Trace getTrace(){
        return TRACE_THREAD_LOCAL.get();
    }

    public static void removeTrace(){
        TRACE_THREAD_LOCAL.remove();
        MDC.remove(BaseConstant.TRACE_ID);
    }

    public static void setToken(Token token){
        TOKEN_THREAD_LOCAL.set(token);
    }

    public static void removeToken(){
        TOKEN_THREAD_LOCAL.remove();
    }

    public static Token getToken(){
        return TOKEN_THREAD_LOCAL.get();
    }


}
