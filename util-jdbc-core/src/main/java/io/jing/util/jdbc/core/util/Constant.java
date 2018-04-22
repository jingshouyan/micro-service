package io.jing.util.jdbc.core.util;

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
}
