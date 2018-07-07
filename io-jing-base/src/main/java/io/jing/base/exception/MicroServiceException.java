package io.jing.base.exception;

import io.jing.base.util.code.Code;
import lombok.Getter;

/**
 * @author jingshouyan
 * @date 2018/4/15 1:43
 */
public class MicroServiceException extends RuntimeException {
    @Getter
    private int code;
    @Getter
    private Object data;
    @Getter
    private String msg;

    public MicroServiceException(int code){
        this.code = code;
        this.msg = Code.getMessage(code);
    }

    public MicroServiceException(int code,Object data){
        this.code = code;
        this.msg = Code.getMessage(code);
        this.data = data;
    }

    public MicroServiceException(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public MicroServiceException(int code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public MicroServiceException(int code, Throwable cause){
        super(cause);
        this.code = code;
        this.msg = Code.getMessage(code);
    }

}
