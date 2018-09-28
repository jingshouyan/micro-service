package io.jing.server.db.aop;


import io.jing.server.constant.ServerConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jingshouyan
 */
@Component
@Aspect
@Slf4j(topic = "JDBC-TEMPLATE-SQL")
public class Log4Sql {

    private static boolean showSql = ServerConstant.SQL_LOG_SHOW;

    @Pointcut("bean(*JdbcTemplate)")
    public void aspect() {
    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        if(!showSql){
            return joinPoint.proceed();
        }
        long start = System.currentTimeMillis();
        try {
            Object[] args = joinPoint.getArgs();
            log.info("sql execution starting");
            for (int i = 0; i < args.length; i++) {
                log.info("arg.{}===>{}", i, args[i]);
            }
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            long fetch = 0;
            if (result instanceof List) {
                fetch = ((List) result).size();
            } else {
                try {
                    fetch = Long.valueOf(String.valueOf(result));
                } catch (Exception e) {
                }
            }
            log.info("sql execution end. use time : {}ms, fetch : {}", (end - start), fetch);
            log.info("sql execution end. result : {} ", result);
            return result;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            if (log.isErrorEnabled()) {
                log.error("call {} error. use time : {}ms", joinPoint.toShortString(), (end - start), e);
            }
            throw e;
        }

    }
}
