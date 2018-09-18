package io.jing.server.sign.controller;

import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.code.Code;
import io.jing.base.util.rsp.RspUtil;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.client.util.ClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author jingshouyan
 * #date 2018/9/17 23:22
 */
@RestController
@Slf4j
public class ApiController {

    @RequestMapping(path = "/{service}/{method}")
    public Rsp call(@PathVariable String service, @PathVariable String method, HttpServletRequest request){
        Rsp rsp;
        try(InputStream in = request.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();){
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf))>0){
                out.write(buf,0,len);
            }
            String data = out.toString("utf-8");
            log.info("{}.{} data:{}",service,method,data);
            if(StringUtils.isEmpty(data)){
                data = "{}";
            }
            String str = service+"."+method;
            Token token = ThreadLocalUtil.getToken();
            Req req = Req.builder().service(service).method(method).param(data).build();
            rsp = ClientUtil.call(token,req);
        }catch (MicroServiceException e) {
            rsp = RspUtil.error(e);
        }catch (Exception e) {
            log.error("api error.",e);
            rsp = RspUtil.error(Code.SERVER_ERROR,e);
        }
        return rsp;
    }
}
