package io.jing.server.sign.controller;

import io.jing.base.bean.Rsp;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.code.Code;
import io.jing.base.util.rsp.RspUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public String handleErr(HttpServletRequest request,Exception e){
        Rsp rsp;
        if(e instanceof MicroServiceException){
            rsp = RspUtil.error((MicroServiceException)e);
        }else {
            rsp = RspUtil.error(Code.SERVER_ERROR,e);
            log.warn("error",e);
        }
        return rsp.json();
    }
}
