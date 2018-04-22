package io.jing.base.bean;

import com.alibaba.fastjson.JSON;
import io.jing.base.thrift.RspBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author jingshouyan
 * @date 2018/4/14 22:43
 */
@Data@Builder@AllArgsConstructor
public class Rsp {
    private int code;
    private String message;
    private Object data;
    private String result;

    public Rsp() { }
    public Rsp(RspBean rspBean) {
        code = rspBean.getCode();
        message = rspBean.getMsg();
        result = rspBean.getResult();
    }

    public RspBean rspBean(){
        return new RspBean()
                .setCode(code)
                .setMsg(message)
                .setResult(JSON.toJSONString(data));
    }

    public <T> T get(Class<T> clazz){
        return JSON.parseObject(result,clazz);
    }

    public <T> List<T> list(Class<T> clazz){
        return JSON.parseArray(result,clazz);
    }
}
