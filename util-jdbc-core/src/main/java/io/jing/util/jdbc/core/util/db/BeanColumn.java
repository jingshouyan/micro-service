package io.jing.util.jdbc.core.util.db;

import io.jing.util.jdbc.core.util.Constant;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * 对象属性
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
@Data
public class BeanColumn {
    private Field field;
    private String fieldName;
    private String columnName;
    private int columnLength = Constant.COLUMN_LENGTH_DEFAULT;
    private String columnType;
    private boolean encrypt = false;
    private String pwd = Constant.COLUMN_ENCRYPT_KEY_DEFAULT;
    private boolean keyColumn = false;
    private boolean autoGen = false;
    private boolean index = false;
    private String comment = "";
    private int order = Constant.COLUMN_ORDER_DEFAULT;
}
