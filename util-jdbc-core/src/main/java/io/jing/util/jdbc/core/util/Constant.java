package io.jing.util.jdbc.core.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

/**
 * Constant
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
public interface Constant {
    int COLUMN_ORDER_DEFAULT = 10;
    int COLUMN_LENGTH_DEFAULT = 255;
    int VARCHAR_MAX_LENGTH = 5000;

    String COLUMN_ENCRYPT_KEY_PREFIX_FIXED = "fixed:";
    String COLUMN_ENCRYPT_KEY_PREFIX_FIELD = "field:";
    String COLUMN_ENCRYPT_KEY_DEFAULT = COLUMN_ENCRYPT_KEY_PREFIX_FIXED +"abc1234_Linkdood";

    public static final ObjectMapper OBJECT_MAPPER = Optional.<ObjectMapper>empty().orElseGet(()->{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return objectMapper;
    });

}
