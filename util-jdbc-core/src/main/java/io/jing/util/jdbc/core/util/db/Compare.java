package io.jing.util.jdbc.core.util.db;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Compare
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
@Data
@ToString
public class Compare {

    /**
     * 属性名
     */
    private String field;

    /**
     * 模糊匹配，需要包含 % 等占位符
     */
    private String like;

    /**
     * 大于
     */
    private Object gt;

    /**
     * 小于
     */
    private Object lt;

    /**
     * 大于等于
     */
    private Object gte;

    /**
     * 小于等于
     */
    private Object lte;

    /**
     * 等于
     */
    private Object eq;

    /**
     * 不等于
     */
    private Object ne;

    /**
     * 在范围
     */
    private List<?> in;

    /**
     * 不在范围
     */
    private List<?> notIn;

    /**
     * 是否为空
     */
    private Boolean empty;


}
