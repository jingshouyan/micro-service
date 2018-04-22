package io.jing.base.bean;

import com.alibaba.fastjson.JSON;
import io.jing.base.thrift.ReqBean;
import io.jing.base.thrift.TokenBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author jingshouyan
 * @date 2018/4/14 22:34
 */
@Data@Builder@AllArgsConstructor
public class Req {
    private String service;
    private String method;
    private String param;
    private Object paramObj;
    private String router;
    private boolean oneWay;

    public Req() { }

    public Req(ReqBean reqBean) {
        method = reqBean.getMethod();
        param = reqBean.getParam();
        oneWay = reqBean.isOneWay();
    }
    public ReqBean reqBean(){
        String p = param;
        if(null == p){
            p = JSON.toJSONString(paramObj);
        }
        return new ReqBean()
                .setMethod(method)
                .setParam(p)
                .setOneWay(oneWay);
    }
}
