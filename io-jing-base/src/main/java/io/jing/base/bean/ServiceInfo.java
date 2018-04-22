package io.jing.base.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author jingshouyan
 * @date 2018/4/14 22:12
 */
@Data
public class ServiceInfo {
    public ServiceInfo(){}

    private String id;
    private String name;
    private String version;
    private String host;
    private int port;
    private String startAt;
    private String updatedAt;
    private int timeout = 5000;
    private int maxReadBufferBytes = 25 * 1024 * 1024;

    public String key(){
        return host+":"+port;
    }

    public void key(String key){
        String[] strings = key.split(":");
        host = strings[0];
        port = Integer.parseInt(strings[1]);
    }

    public void update(ServiceInfo serviceInfo){

    }
}
