package io.jing.server.sms.controller;

import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.iface.MicroServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author jingshouyan
 * #date 2018/6/28 19:59
 */
@RestController
@Slf4j
public class Api {

    @Autowired
    private MicroServiceImpl microService;

    @RequestMapping(path = "v1/{method}.json")
    public Rsp v1(@PathVariable String method, @RequestBody String data){
        ThreadLocalUtil.setTraceId(UUID.randomUUID().toString().toLowerCase());
        Token token = new Token();
        Req req = Req.builder().method(method).param(data).build();
        return microService.run(token,req);
    }
}
