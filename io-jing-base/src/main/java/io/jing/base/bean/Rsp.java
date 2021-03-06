package io.jing.base.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jing.base.thrift.RspBean;
import io.jing.base.util.json.JsonUtil;
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
    @JsonIgnore
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
                .setResult(JsonUtil.toJsonString(data));
    }

    public <T> T get(Class<T> clazz){
        return JsonUtil.toBean(result,clazz);
    }

    public <T> T get(Class<T> clazz,Class<?> classes){
        return JsonUtil.toBean(result,clazz,classes);
    }

    public <T> List<T> list(Class<T> clazz){
        return JsonUtil.toList(result,clazz);
    }

    public String json(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"code\":");
        sb.append(code);
        sb.append(",\"message\":\"");
        sb.append(message);
        sb.append("\"");
        if (result == null && data != null) {
            result = JsonUtil.toJsonString(data);
        }
        if(result != null){
            sb.append(",\"data\":");
            sb.append(result);
        }
        sb.append("}");
        return sb.toString();
    }
}
