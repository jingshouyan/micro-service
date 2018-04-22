package io.jing.util.jdbc.core.util.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * BeanTable
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
@Data
public class BeanTable {

    private Class<?> clazz;

    private String tableName;

    private String comment = "";

    private BeanColumn key;

    private List<BeanColumn> columns = Lists.newArrayList();

    private Set<BeanColumn> encryptColumns = Sets.newHashSet();

    private Map<Field, BeanColumn> fieldMap = Maps.newHashMap();

    private Map<String, BeanColumn> fieldNameMap = Maps.newHashMap();

    private Map<String, BeanColumn> columnMap = Maps.newHashMap();

    /**
     * 添加列信息
     *
     * @param column 列信息
     */
    public void addBeanColumn(BeanColumn column) {
        columns.add(column);
        fieldMap.put(column.getField(), column);
        fieldNameMap.put(column.getFieldName(), column);
        columnMap.put(column.getColumnName(), column);
        //加密列
        if (column.isEncrypt()) {
            encryptColumns.add(column);
        }
        if (column.isKeyColumn() && key == null) {
            //取第一个为 key
            //因为是先取 类 中的属性，然后再取 父类 中的属性
            key = column;
        }
    }

}
