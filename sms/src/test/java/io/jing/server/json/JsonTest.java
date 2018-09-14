package io.jing.server.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.collect.Maps;
import io.jing.base.util.json.JsonUtil;
import io.jing.server.sms.bean.UserBean;

import java.util.Map;
import java.util.Optional;

public class JsonTest {

    public static void main(String[] args) throws Exception {
        UserBean userBean = new UserBean();
        userBean.setId("1");
        userBean.setUsername("2");
        userBean.setPwHash("3");
        userBean.setNickname("4");
        userBean.setIcon("5");
//        userBean.setUserType(6);
        userBean.setCreatedAt(7L);
        userBean.setUpdatedAt(8L);
        userBean.setDeletedAt(9L);

        Map<String,Object> map = Maps.newConcurrentMap();
        map.put("abc",userBean);
        String str = JsonUtil.toJsonString(userBean);
        String str2 = "{\"createdAt\":7,\"updatedAt\":8,\"deletedAt\":9,\"id\":\"1\",\"username\":\"2\",\"pwHash\":\"3\",\"nickname\":\"4\",\"icon\":\"5\",\"userType\":6}";
        UserBean userBean1 = OBJECT_MAPPER.readValue(str2,UserBean.class);
        System.out.println(str);
        System.out.println(userBean1);

        OBJECT_MAPPER.addMixIn(UserBean.class,PropertyFilterMixIn.class);
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("userBean",SimpleBeanPropertyFilter.serializeAllExcept("id"));
        OBJECT_MAPPER.setFilterProvider(filterProvider);
        String str3 = OBJECT_MAPPER.writeValueAsString(userBean);
        System.out.println(str3);
        String str4 = OBJECT_MAPPER.writeValueAsString(map);
        System.out.println(map);
    }

    private static final ObjectMapper OBJECT_MAPPER = Optional.<ObjectMapper>empty().orElseGet(()->{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE,false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    });

}
