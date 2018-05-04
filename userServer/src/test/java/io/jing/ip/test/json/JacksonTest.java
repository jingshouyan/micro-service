package io.jing.ip.test.json;

import com.fasterxml.jackson.databind.JsonNode;
import io.jing.base.constant.BaseConstant;
import io.jing.base.util.json.JsonUtil;
import io.jing.server.user.constant.UserConstant;

/**
 * @author jingshouyan
 * @date 2018/5/4 10:08
 */
public class JacksonTest implements UserConstant {

    public static void main(String[] args) throws Exception {
        String str2 = "[{\"userId\":123,\"ticket\":\"1234\",\"ticket2\":\"123\"}]";
        JsonNode jsonNode = JsonUtil.toJsonNode(str2);

        System.out.println(jsonNode);

        Object  obj = JsonUtil.get(jsonNode,"[0].userId");

        System.out.println(obj);

        String s = JsonUtil.toJsonString("1");
        System.out.println(s);
        jsonNode = JsonUtil.toJsonNode(s);
        System.out.println(jsonNode);

    }
}
