package io.jing.base.util.rsp;

import io.jing.base.bean.Rsp;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.code.Code;

/**
 * @author jingshouyan
 * @date 2018/4/15 14:30
 */
public class RspUtil {
    public static Rsp success() {
        return success(null);
    }

    public static Rsp success(Object result) {
        return error(Code.SUCCESS, result, null);
    }

    public static Rsp error(int code) {
        return error(code, null, null);
    }

    public static Rsp error(int code, Throwable e) {
        return error(code, null, e);
    }

    public static Rsp error(int code, Object result) {
        return error(code, result, null);
    }

    public static Rsp error(MicroServiceException e){
        return error(e.getCode(),e.getMsg(),e.getData(),e);
    }

    /**
     * @param code 错误码
     * @param result  返回对象
     * @param e       异常信息
     * @return Rsp对象  msg根据code对应的消息 result json序列化
     * @Description 生成Rsp对象
     */
    public static Rsp error(int code, Object result, Throwable e) {
        return error(code,Code.getMessage(code),result,e);
    }

    private static Rsp error(int code,String message,Object data,Throwable e){
        Rsp res = new Rsp();
        res.setCode(code);
        res.setData(data);
        if (null != e && null != e.getMessage()) {
            message = message + "|" + e.getMessage();
        }
        res.setMessage(message);
        return res;
    }

}
