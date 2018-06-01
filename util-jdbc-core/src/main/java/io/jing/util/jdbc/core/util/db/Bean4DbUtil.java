package io.jing.util.jdbc.core.util.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.aes.AesUtil;
import io.jing.util.jdbc.core.util.bean.StringUtil;
import io.jing.util.jdbc.core.util.db.annotation.Ignore;
import io.jing.util.jdbc.core.util.db.annotation.Table;
import io.jing.util.jdbc.core.util.keygen.IdUtil;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/**
 * 主键
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
@Slf4j
public class Bean4DbUtil implements Constant{
    private static final Map<Class<?>, BeanTable> BEAN_MAP = Maps.newConcurrentMap();
    public static Set<String> getFieldNameSet(Class<?> clazz) {
        return getBeanTable(clazz).getFieldNameMap().keySet();
    }

    /**
     * 获取 属性，列名 map
     * @param clazz 类
     * @return map
     */
    public static Map<String, String> fieldColumnMap(Class<?> clazz) {
        return Maps.transformValues(getBeanTable(clazz).getFieldNameMap(), BeanColumn::getColumnName);
    }

    /**
     * 获取 class 定义的 主键 名
     *
     * @param clazz class
     * @return 主键 名
     */
    public static String getKeyFieldName(Class<?> clazz) {
        BeanColumn key = getBeanTable(clazz).getKey();
        Preconditions.checkArgument(null != key, "class[" + clazz + "] is not set key");
        return key.getFieldName();
    }


    /**
     * 获取对象对应属性的值
     * @param bean 对象
     * @param fieldName 属性名
     * @return 属性值
     */
    @SneakyThrows
    public static Object getFieldValue(Object bean, String fieldName) {
        BeanColumn column = getBeanTable(bean.getClass()).getFieldNameMap().get(fieldName);
        Preconditions.checkNotNull(column, bean.getClass().toString() + " don't have field:" + fieldName);
        Field field = column.getField();
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        return field.get(bean);
    }

    /**
     * 设置对象的值
     * @param bean 对象
     * @param fieldName 属性名
     * @param value 值
     */
    @SneakyThrows
    public static void setStringFieldValue(Object bean, String fieldName, String value) {
        BeanColumn column = getBeanTable(bean.getClass()).getFieldNameMap().get(fieldName);
        Preconditions.checkArgument(null != column, bean.getClass().toString() + " don't have field:" + fieldName);
        Field field = column.getField();
        Preconditions.checkArgument(field.getType() == String.class, field + " isn't String");
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        field.set(bean, value);
    }

    /**
     * 设置对象主键
     * @param bean 对象
     * @param key4Set 值
     */
    @SneakyThrows
    private static void setKey(Object bean,Object key4Set){
        BeanColumn key = getBeanTable(bean.getClass()).getKey();
        if (null != key && key.isAutoGen()) {
            Field field = key.getField();
            boolean accessible = field.isAccessible();
            if (!accessible) {
                field.setAccessible(true);
            }
            Object fieldValue = field.get(bean);
            if (null == fieldValue) {
                Class<?> clazz = field.getType();
                if (clazz == Long.class || clazz == long.class) {
                    field.set(bean, (long)key4Set);
                } else if (clazz == String.class) {
                    field.set(bean, String.valueOf(key4Set));
                } else {
                    throw new IllegalArgumentException("key[" + field.getName() + "] messageType must be long|string.");
                }
            }
        }
    }

    /**
     * 设置对象的主键
     * @param bean 对象
     */
    public static void genKey(Object bean) {
        long id = IdUtil.longId();
        setKey(bean,id);
    }

    /**
     * 新建对象 并设置主键
     * @param clazz 对象类型
     * @param key 主键值
     * @param <T>
     * @return 对象
     */
    @SneakyThrows
    public static <T> T newInstanceWithKey(Class<T> clazz,Object key){
        T t = clazz.newInstance();
        setKey(t,key);
        return t;
    }

    /**
     * 加密
     * @param bean 对象
     */
    public static void encrypt(Object bean){
        encryptOrDecryptBean(bean,true);
    }

    /**
     * 解密
     * @param bean 对象
     */
    public static void decrypt(Object bean){
        encryptOrDecryptBean(bean,false);
    }

    private static void encryptOrDecryptBean(Object bean, boolean decrypt) {
        BeanTable table = getBeanTable(bean.getClass());
        Set<BeanColumn> encryptColumns = table.getEncryptColumns();
        if (!encryptColumns.isEmpty()) {
            for (BeanColumn column : encryptColumns) {
                String fieldName = column.getFieldName();
                Object obj = getFieldValue(bean, fieldName);
                if (null == obj) {
                    continue;
                }
                String pwd = column.getPwd();
                String password = null;
                if(pwd.startsWith(Constant.COLUMN_ENCRYPT_KEY_PREFIX_FIXED)){
                    password = pwd.substring(Constant.COLUMN_ENCRYPT_KEY_PREFIX_FIXED.length());
                }else if(pwd.startsWith(Constant.COLUMN_ENCRYPT_KEY_PREFIX_FIELD)){
                    pwd = pwd.substring(Constant.COLUMN_ENCRYPT_KEY_PREFIX_FIELD.length());
                    Object objKey = getFieldValue(bean, pwd);
                    Preconditions.checkNotNull(objKey, "key field is null");
                    password = String.valueOf(objKey);
                }
                Preconditions.checkNotNull(password,"encrypt/decrypt password is null");
                String fieldValue = String.valueOf(obj);

                if (decrypt) {
                    fieldValue = AesUtil.encrypt(fieldValue, password);
                } else {
                    fieldValue = AesUtil.decrypt(fieldValue, password);
                }
                setStringFieldValue(bean, fieldName, fieldValue);
            }
        }
    }


    /**
     * 对象转map
     * @param bean 对象
     * @return map
     */
    @SneakyThrows
    public static Map<String, Object> bean2Map(Object bean) {
        if (null == bean){
            return null;
        }
        Map<String, Object> map = Maps.newHashMap();
        BeanTable beanTable = getBeanTable(bean.getClass());
        for (BeanColumn beanColumn : beanTable.getColumns()) {
            Field field = beanColumn.getField();
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            Object value = field.get(bean);
            if(null == value) {
                continue;
            }
            if(beanColumn.isJson()){
                value = OBJECT_MAPPER.writeValueAsString(value);
            }
            map.put(field.getName(), value);
        }
        return map;
    }

    /**
     * 获取 属性对应的 列名
     * @param clazz 类
     * @param fieldName 属性
     * @return 列明
     */
    public static String getColumnName(Class<?> clazz, String fieldName) {
        BeanTable beanTable = getBeanTable(clazz);
        BeanColumn beanColumn = beanTable.getFieldNameMap().get(fieldName);
        Preconditions.checkArgument(null != beanColumn
                , String.format("[%s] dos not contains field [%s]", clazz, fieldName));
        return beanColumn.getColumnName();
    }

    /**
     * 获取 class 对应的 beanTable
     * @param clazz
     * @return beanTable
     */
    @Synchronized
    public static BeanTable getBeanTable(Class<?> clazz) {
        return BEAN_MAP.computeIfAbsent(clazz,Bean4DbUtil::clazz2BeanTable);
    }
    /**
     * 生成 class 对应的 beanTable
     * @param clazz
     * @return beanTable
     */
    private static BeanTable clazz2BeanTable(Class<?> clazz) {
        BeanTable beanTable = new BeanTable();
        beanTable.setClazz(clazz);
        Table table = clazz.getAnnotation(Table.class);
        if (null != table && !Strings.isNullOrEmpty(table.value())) {
            beanTable.setTableName(table.value());
            beanTable.setComment(table.comment());
        } else {
            beanTable.setTableName(StringUtil.camel2Underline(clazz.getSimpleName()));
        }
        //属性名 用于排除 重名的 父类中的属性
        Set<String> fieldNames = Sets.newConcurrentHashSet();
        for (Class c = clazz; Object.class != c; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                //静态属性
                if (Modifier.isStatic(mod)) {
                    continue;
                }
                //排除添加 @Ignore 的属性
                if (field.isAnnotationPresent(Ignore.class)) {
                    continue;
                }
                if (!fieldNames.contains(field.getName())) {
                    fieldNames.add(field.getName());
                    BeanColumn beanColumn = new BeanColumn(field);
                    beanTable.addBeanColumn(beanColumn);
                }
            }
        }
        beanTable.getColumns().sort(Comparator.comparing(BeanColumn::getOrder));
        return beanTable;
    }


}
