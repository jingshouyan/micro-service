package io.jing.server.gateway.controller;

import com.google.common.collect.Sets;
import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.util.code.Code;
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
import java.util.Set;
import java.util.UUID;

/**
 * @author jingshouyan
 * #date 2018/6/17 7:45
 */
@RestController
@Slf4j
public class Api {

    @RequestMapping(path = "api/{service}/{method}.json")
    public String api(@PathVariable String service, @PathVariable String method, HttpServletRequest request) throws Exception{
        try(InputStream in = request.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();){
            String traceId = request.getHeader("traceId");
            String ticket = request.getHeader("ticket");
            traceId = traceId + "."+UUID.randomUUID();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = in.read(buf))>0){
                out.write(buf,0,len);
            }
            String data = out.toString();
            log.info("{}.{} data:{}",service,method,data);
            if(StringUtils.isEmpty(data)){
                data = "{}";
            }
            ThreadLocalUtil.setTraceId(traceId);
            String str = service+"."+method;
            Token token = new Token();
            if(!NO_AUTH.contains(str)){
                String param = "{\"ticket\":\""+ticket+"\"}";
                Req req = Req.builder().service("user").method("GetToken").param(param).build();
                Rsp rsp = ClientUtil.call(token,req);
                if(rsp.getCode()!=Code.SUCCESS){
                    return rsp.json();
                }
                token = rsp.get(Token.class);
            }
            Req req = Req.builder().service(service).method(method).param(data).build();
            Rsp rsp = ClientUtil.call(token,req);
            return rsp.json();
        }catch (Exception e) {
            return "{\"code\":"+Code.SERVER_ERROR+",\"message\":\""+Code.getMessage(Code.SERVER_ERROR)+"\"}";
        }

    }

    private static final Set<String> NO_AUTH = Sets.newHashSet("user.RegUser","user.Login");

}
