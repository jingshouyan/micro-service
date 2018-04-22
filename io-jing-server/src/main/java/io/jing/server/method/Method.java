package io.jing.server.method;

import io.jing.base.bean.Rsp;
import io.jing.base.exception.InvalidException;
import io.jing.base.util.code.Code;
import io.jing.base.util.threadlocal.ThreadLocalUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author jingshouyan
 * @date 2018/4/15 0:32
 */
public interface Method<T> {

    Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 获取当前请求 userId
     * @return userId
     */
    default String getUserId(){
        String userId = ThreadLocalUtil.getToken().getUserId();
        assert userId != null;
        return userId;
    }

    /**
     * 子类调用时获取接口中的泛型
     * @return 泛型
     */
    @SuppressWarnings("unchecked")
    default Class<T> getClazz() {
        Class<T> clazz = null;
        Type[] ts = getClass().getGenericInterfaces();
        for (int i = 0; i < ts.length; i++) {
            if(ts[i] instanceof ParameterizedType){
                ParameterizedType t = (ParameterizedType)ts[i];
                if (t.getRawType() == Method.class){
                    clazz = (Class<T>)t.getActualTypeArguments()[0];
                }
            }
        }
        return clazz;
    }

    /**
     * 校验请求参数
     * @param t 参数
     */
    default void validate(T t){
        Set<ConstraintViolation<T>> cvs = VALIDATOR.validate(t);
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<T> cv : cvs) {
            sb.append(cv.getPropertyPath().toString());
            sb.append(" ");
            sb.append(cv.getMessage());
            sb.append("\t");
        }
        if(!cvs.isEmpty()){
            throw new InvalidException(Code.PARAM_INVALID,sb.toString());
        }
    }

    /**
     * 执行业务
     * @param t 入参
     * @return 执行结果
     */
    default Object actionWithValidate(T t){
        validate(t);
        return action(t);
    }

    /**
     * 执行业务
     * @param t 入参
     * @return 执行结果
     */
    Object action(T t);

}
