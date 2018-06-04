package io.jing.server.user.controller;

import io.jing.server.zk.Register;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jingshouyan
 * @date 2018/4/17 11:23
 */
@RestController
public class Hello {
    @RequestMapping("hello")
    public Object hello(){
        return Register.SERVICE_INSTANCE;
    }




//    @RequestMapping("index")
//    public Object index(){
//        return Register.instanceName();
//    }

    @RequestMapping("prop")
    public Object properties(){
        return System.getProperties();
    }
}
