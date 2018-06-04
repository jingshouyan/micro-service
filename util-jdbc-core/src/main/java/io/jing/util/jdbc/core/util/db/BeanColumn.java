package io.jing.util.jdbc.core.util.db;

import com.google.common.base.Strings;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
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
    private boolean json = false;
    private boolean list = false;
    private boolean encrypt = false;
    private String pwd = Constant.COLUMN_ENCRYPT_KEY_DEFAULT;
    private boolean keyColumn = false;
    private boolean autoGen = false;
    private boolean index = false;
    private String comment = "";
    private int order = Constant.COLUMN_ORDER_DEFAULT;


    public BeanColumn(Field field){
        this.setField(field);
        this.setFieldName(field.getName());
        Column column = field.getAnnotation(Column.class);
        if (null != column) {
            //设置列明
            this.setColumnName(Strings.isNullOrEmpty(column.value()) ? field.getName() : column.value());
            this.setColumnLength(column.length());
            this.setColumnType(column.type());
            this.setEncrypt(column.encrypt());
            this.setPwd(column.encryptKey());
            this.setIndex(column.index());
            this.setComment(column.comment());
            this.setOrder(column.order());
            this.setJson(column.json());
        } else {
            this.setColumnName(field.getName());
        }
        //是否添加了 @Key 注解
        Key key = field.getAnnotation(Key.class);
        if (null != key) {
            this.setKeyColumn(true);
            this.setAutoGen(key.generatorIfNotSet());
        }
    }

}
