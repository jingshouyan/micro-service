package io.jing.server.user.controller;

import com.google.common.collect.Maps;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import io.jing.server.zk.Register;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;

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
