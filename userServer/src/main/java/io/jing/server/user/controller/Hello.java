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
    public String hello(){
        return getRealPath();
    }

    private static DatabaseReader reader ;
    static {
        try{
            InputStream in = Hello.class.getClassLoader().getResourceAsStream("GeoLite2-City.mmdb");
            reader = new DatabaseReader.Builder(in).build();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @RequestMapping("ip")
    public String ip(@RequestParam String ip) throws Exception{
        String address = "";
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = reader.city(ipAddress);
        address += response.getCountry().getNames().get("zh-CN")+ " ";
        address += response.getMostSpecificSubdivision().getNames().get("zh-CN")+ " ";
        address += response.getCity().getNames().get("zh-CN")+ "";
        return address;
    }


    @SneakyThrows
    public String getRealPath() {
        URL url =  getClass().getClassLoader().getResource("GeoLite2-City.mmdb");
        File file = new File(url.toURI());
        String realPath = file.getAbsolutePath();
        try {
            realPath = java.net.URLDecoder.decode (realPath, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return realPath;
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
