package io.jing.server.method.factory;

import com.google.common.collect.Maps;
import io.jing.base.exception.InvalidException;
import io.jing.base.util.code.Code;
import io.jing.server.method.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author jingshouyan
 * @date 2018/4/15 14:41
 */
@Component@Slf4j
public class MethodFactory {

    private static final Map<String, Method> METHOD_MAP = Maps.newConcurrentMap();

    @Autowired
    private ApplicationContext ctx;
    @PostConstruct
    private void init() {
        Map<String, Method> methods = ctx.getBeansOfType(Method.class);
        for (Method method : methods.values()) {
            register(method);
        }
    }

    public static void register(Method method){
        String methodName = method.getClass().getSimpleName();
        METHOD_MAP.put(methodName,method);
        log.info("method register [{} = {}]", methodName, method);
    }

    public static Method getMethod(String methodName){
        return METHOD_MAP.computeIfAbsent(methodName,
                m -> {throw new InvalidException(Code.METHOD_NOT_FOUND);}
                );
    }
}
