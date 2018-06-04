package io.jing.base.exception;

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

    public MicroServiceException(int code){
        this.code = code;
    }

    public MicroServiceException(int code, String message){
        super(message);
        this.code = code;
    }

    public MicroServiceException(int code, Throwable cause){
        super(cause);
        this.code = code;
    }

    public MicroServiceException(int code, String message, Throwable cause){
        super(message, cause);
        this.code = code;
    }
}
