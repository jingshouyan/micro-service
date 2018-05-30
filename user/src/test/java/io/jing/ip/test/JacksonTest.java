package io.jing.ip.test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jing.base.bean.Token;

/**
 * @author jingshouyan
 * @date 2018/5/3 19:30
 */
public class JacksonTest {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();;
    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static void main(String[] args) throws Exception{
        Token token = Token.builder().ticket("123").build();
        String str = OBJECT_MAPPER.writeValueAsString(token);
        System.out.println(str);
        String str2 = "{\"userId\":null,\"ticket\":\"1234\",\"ticket2\":\"123\"}";
        Token token1 = OBJECT_MAPPER.readValue(str2,Token.class);
        System.out.println(token1);
    }
}
