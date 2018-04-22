package io.jing.base.exception;

import io.jing.base.util.code.Code;
import lombok.Getter;

/**
 * @author jingshouyan
 * @date 2018/4/15 1:43
 */
public class InvalidException extends RuntimeException {
    @Getter
    private int code;
    @Getter
    private Object data;

    public InvalidException(int code){
        this.code = code;
    }

    public InvalidException(int code,String message){
        super(message);
        this.code = code;
    }

    public InvalidException(int code, Throwable cause){
        super(cause);
        this.code = code;
    }

    public InvalidException(int code, String message, Throwable cause){
        super(message, cause);
        this.code = code;
    }
}
