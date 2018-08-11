package io.jing.server.method.factory;

import com.google.common.collect.Maps;
import io.jing.base.constant.BaseConstant;
import io.jing.base.exception.MicroServiceException;
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
        methods.forEach((key,method) -> {
            if(BaseConstant.ALL_IN_ONE){
                register(key,method);
            }else {
                String[] ss = key.split("\\.");
                key = ss[ss.length -1];
                register(key,method);
            }
        });
    }

    public static void register(String name,Method method){
        METHOD_MAP.put(name,method);
        log.info("method register [{} = {}]", name, method);
    }

    public static Method getMethod(String methodName){
        return METHOD_MAP.computeIfAbsent(methodName,
                m -> {throw new MicroServiceException(Code.METHOD_NOT_FOUND);}
                );
    }
}
