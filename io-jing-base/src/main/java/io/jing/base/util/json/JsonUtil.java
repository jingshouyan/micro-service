package io.jing.base.util.json;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jing.base.constant.BaseConstant;
import lombok.SneakyThrows;
import org.apache.thrift.TBase;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.protocol.TSimpleJSONProtocol;

import java.io.Closeable;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author jingshouyan
 * @date 2018/4/26 10:20
 */
public class JsonUtil implements BaseConstant {

    private static final ObjectMapper OBJECT_MAPPER = Optional.<ObjectMapper>empty().orElseGet(()->{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    });

    private static final TProtocolFactory factory = new TSimpleJSONProtocol.Factory();

    @SneakyThrows
    public static String toJsonString(TBase base) {
        TSerializer serializer = new TSerializer(factory);
        return serializer.toString(base,"utf-8");
    }
    @SneakyThrows
    public static String toJsonString(Object value) {
        return OBJECT_MAPPER.writeValueAsString(value);
    }
    @SneakyThrows
    public static <T> T toBean(String json, Class<T> clazz){
        return OBJECT_MAPPER.readValue(json,clazz);
    }
    @SneakyThrows
    public static <T> List<T> toList(String json, Class<T> clazz){
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructArrayType(clazz);
        return OBJECT_MAPPER.readValue(json,javaType);
    }
    @SneakyThrows
    public static JsonNode readTree(String json){
        return OBJECT_MAPPER.readTree(json);
    }

    public static JsonNode valueToTree(Object obj){
        return OBJECT_MAPPER.valueToTree(obj);
    }

    /**
     * 获取 json 中 key 的值
     * @param json FastJSON 对象
     * @param key 含层级的key 例：cs.[0]?.b.name
     *            ?. 表示当前层级为null则返回null
     *            . 当前层级为null会抛出NPE
     * @return json 中的值
     */
    public static JsonNode get(JsonNode json, String key){
        String pattern = "^\\[\\d]$";
        String[] keys = key.split("\\.");
        JsonNode obj = json;
        for (int i = 0; i < keys.length; i++) {
            String str = keys[i];
            if(str.endsWith("?")){
                str = str.substring(0,str.length()-1);
                if(null == obj){
                    return null;
                }
            }
            if(Pattern.matches(pattern,str)){
                int index = Integer.parseInt(str.substring(1,str.length()-1));
                obj = obj.get(index);
            }else {
                obj = obj.get(str);
            }
        }
        return obj;
    }
}

