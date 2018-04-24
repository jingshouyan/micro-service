package io.jing.util.jdbc.core.util.db.annotation;

import io.jing.util.jdbc.core.util.Constant;

import java.lang.annotation.*;

/**
 * 列
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    /**
     * @return 列名
     */
    String value() default "";

    /**
     * @return 字段长度
     */
    int length() default Constant.COLUMN_LENGTH_DEFAULT;

    boolean json() default false;
    /**
     * @return 类型
     */
    String type() default "";

    /**
     * @return 是否需要加密 默认否
     */
    boolean encrypt() default false;

    /**
     * @return 加密密钥
     * 1.fixed:abc 以 abc为密钥进行加密
     * 2.flied:id  以 bean 属性 id 的值为密钥进行加密
     */
    String encryptKey() default "";

    /**
     * @return 是否创建索引
     */
    boolean index() default false;

    /**
     * @return 备注
     */
    String comment() default "";

    /**
     * @return 列排序
     */
    int order() default Constant.COLUMN_ORDER_DEFAULT;
}
