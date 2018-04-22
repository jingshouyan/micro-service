package io.jing.base.util.rsp;

import io.jing.base.bean.Rsp;
import io.jing.base.exception.InvalidException;
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

    public static Rsp error(int errCode) {
        return error(errCode, null, null);
    }

    public static Rsp error(int errCode, Throwable e) {
        return error(errCode, null, e);
    }

    public static Rsp error(int errCode, Object result) {
        return error(errCode, result, null);
    }

    public static Rsp error(InvalidException e){
        return error(e.getCode(),e);
    }

    /**
     * @param errCode 错误码
     * @param result  返回对象
     * @param e       异常信息
     * @return Rsp对象  msg根据errCode对应的消息 result json序列化
     * @Description 生成Rsp对象
     */
    public static Rsp error(int errCode, Object result, Throwable e) {
        Rsp res = new Rsp();
        res.setCode(errCode);
        res.setData(result);
        String message = Code.getMessage(errCode);
        if (null != e && null != e.getMessage()) {
            message = message + "|" + e.getMessage();
        }
        res.setMessage(message);
        return res;
    }

    public static Rsp error(int errCode, String message) {
        Rsp res = new Rsp();
        res.setCode(errCode);
        res.setMessage(message);
        return res;
    }
}
