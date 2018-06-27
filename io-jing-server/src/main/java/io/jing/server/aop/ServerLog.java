package io.jing.server.aop;

import io.jing.server.constant.ServerConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 29017 on 2017/8/12.
 */
//@Component
//@Aspect
@Slf4j(topic = "Server-Log")
public class ServerLog {

    private static boolean showLog = true;

    @Pointcut("execution(* io.jing.server.iface.MicroServiceImpl.*(..))")
    public void aspect() {
    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        try {
            if (showLog) {
                Object[] args = joinPoint.getArgs();
                log.info("server call start");
                for (int i = 0; i < args.length; i++) {
                    log.info("arg.{}===>{}", i, args[i]);
                }
            }
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            if (showLog) {
                log.info("server call end.use {}ms,result: {}", (end - start),result);
            }
            return result;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            if (log.isErrorEnabled()) {
                log.error("server call error.use {}ms", joinPoint.toShortString(), (end - start), e);
            }
            throw e;
        }

    }
}
