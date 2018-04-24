package io.jing.util.jdbc.core.dao.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.jing.util.jdbc.core.dao.BaseDao;
import io.jing.util.jdbc.core.event.DmlEventBus;
import io.jing.util.jdbc.core.util.db.*;
import io.jing.util.jdbc.core.util.db.sql.generator.SqlGenerator;
import io.jing.util.jdbc.core.util.db.sql.generator.SqlGeneratorFactory;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


/**
 * 基于 spring-jdbc 的curl 抽象类
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
@Slf4j
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    private Class<T> clazz;
    private RowMapper<T> rowMapper;
    private SqlGenerator<T> sqlGenerator;

    public BaseDaoImpl() {
        init();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        Type t = getClass().getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            clazz = (Class<T>) p[0];
            rowMapper = new BeanRowMapper2<>(clazz);
            sqlGenerator = SqlGeneratorFactory.getSqlGenerator(clazz);
        }
    }

    @Autowired
    protected NamedParameterJdbcTemplate template;


    @Override
    public T find(Object id) {
        List<Compare> compares = CompareUtil.newInstance().field(key()).eq(id).compares();
        List<T> ts = query(compares);
        if (ts.isEmpty()) {
            return null;
        }
        return ts.get(0);
    }


    @Override
    public List<T> findByIds(List<?> ids) {
        List<Compare> compares = CompareUtil.newInstance().field(key()).in(ids).compares();
        return query(compares);
    }


    @Override
    public Page<T> query(List<Compare> compares, Page<T> page) {
        int count = count(compares);
        page.totalCount(count);
        List<T> ts = queryLimit(compares,page);
        page.setList(ts);
        return page;
    }

    @Override
    public List<T> query(List<Compare> compares) {
        SqlPrepared sqlPrepared = sqlGenerator.query(compares);
        List<T> ts = template.query(sqlPrepared.getSql(), sqlPrepared.getParams(), rowMapper);
        //解密
        decrypt(ts);
        return ts;
    }

    @Override
    public List<T> queryLimit(List<Compare> compares,Page<T> page) {
        SqlPrepared sqlPrepared = sqlGenerator.query(compares, page);
        List<T> ts = template.query(sqlPrepared.getSql(), sqlPrepared.getParams(), rowMapper);
        decrypt(ts);
        return ts;
    }


    @Override
    public int update(T t, List<Compare> compares) {
        encrypt(t);
        SqlPrepared sqlPrepared = sqlGenerator.update(t, compares);
        int fetch =  template.update(sqlPrepared.getSql(), sqlPrepared.getParams());
        decrypt(t);
        return fetch;
    }


    @Override
    public int count(List<Compare> compares) {
        SqlPrepared sqlPrepared = sqlGenerator.count(compares);
        return template.queryForObject(sqlPrepared.getSql(), sqlPrepared.getParams(), Integer.class);
    }


    @Override
    public int insert(T t) {
        List<T> list = Lists.newArrayList(t);
        return insert(list);
    }

    private int insert(@NonNull List<T> list) {
        Preconditions.checkArgument(!list.isEmpty(), "list is empty!");
        for (T t : list) {
            //如果不设置主键，则使用 keygen 生成主键
            genKey(t);
            encrypt(t);
        }
        SqlPrepared sqlPrepared = sqlGenerator.insert(list);
        int fetch = template.update(sqlPrepared.getSql(), sqlPrepared.getParams());
        for (T t : list) {
            decrypt(t);
            //添加插入事件
            DmlEventBus.onCreate(t);
        }
        return fetch;
    }

    @Override
    public int batchInsert(@NonNull List<T> list) {
        Preconditions.checkArgument(!list.isEmpty(), "list is empty!");
        SqlPrepared sqlPrepared = null;
        List<Map<String, Object>> values = Lists.newArrayList();
        Map[] v = new Map[list.size()];
        for (T t : list) {
            //如果不设置主键，则使用 keygen 生成主键
            genKey(t);
            encrypt(t);
            List<T> ts = Lists.newArrayList(t);
            sqlPrepared = sqlGenerator.insert(ts);
            values.add(sqlPrepared.getParams());
        }
        v = values.toArray(v);
        int[] fetches = template.batchUpdate(sqlPrepared.getSql(), v);
        int fetch = IntStream.of(fetches).sum();
        for (T t : list) {
            decrypt(t);
            //添加插入事件
            DmlEventBus.onCreate(t);
        }
        return fetch;
    }

    @Override
    public int update(T t) {
        String key = key();
        Object value = Bean4DbUtil.getFieldValue(t, key);
        Preconditions.checkNotNull(value, "The @Key field must not null");
        List<Compare> compares = CompareUtil.newInstance().field(key()).eq(value).compares();
        int fetch = update(t, compares);
        //添加更新事件
        DmlEventBus.onUpdate(t);
        return fetch;
    }

    @Override
    public int batchUpdate(List<T> list){
        Preconditions.checkArgument(!list.isEmpty(), "list is empty!");
        SqlPrepared sqlPrepared = null;
        List<Map<String, Object>> values = Lists.newArrayList();
        Map[] v = new Map[list.size()];
        for (T t : list) {
            encrypt(t);
            String key = key();
            Object value = Bean4DbUtil.getFieldValue(t, key);
            Preconditions.checkNotNull(value, "The @Key field must not null");
            List<Compare> compares = CompareUtil.newInstance().field(key()).eq(value).compares();
            sqlPrepared = sqlGenerator.update(t,compares);
            values.add(sqlPrepared.getParams());
        }
        v = values.toArray(v);
        int[] fetches = template.batchUpdate(sqlPrepared.getSql(), v);
        int fetch = IntStream.of(fetches).sum();
        for (T t : list) {
            decrypt(t);
            //添加更新事件
            DmlEventBus.onUpdate(t);
        }
        return fetch;
    }

    @Override
    public int delete4List(List<Object> ids) {
        List<Compare> compares = CompareUtil.newInstance().field(key()).in(ids).compares();
        int fetch = delete4Batch(compares);
        //添加删除事件
        if(DmlEventBus.isDeleteOn()){
            ids.forEach((id)->{
                T t = Bean4DbUtil.newInstanceWithKey(clazz,id);
                DmlEventBus.onDelete(t);
            });
        }
        return fetch;
    }

    @Override
    public int delete(Object... ids) {
        List<Object> l = Lists.newArrayList(ids);
        return delete4List(l);
    }

    @Override
    public int delete4Batch(List<Compare> compares) {
        SqlPrepared sqlPrepared = sqlGenerator.delete(compares);
        return template.update(sqlPrepared.getSql(), sqlPrepared.getParams());
    }




    @Override
    public int createTable() {
        SqlPrepared sqlPrepared = sqlGenerator.createTableSql();
        return template.update(sqlPrepared.getSql(), sqlPrepared.getParams());
    }

    @Override
    public int dropTable() {
        SqlPrepared sqlPrepared = sqlGenerator.dropTableSql();
        return template.update(sqlPrepared.getSql(), sqlPrepared.getParams());
    }


    private String key() {
        return Bean4DbUtil.getKeyFieldName(clazz);
    }

    /**
     * 生成主键
     *
     * @param t 对象
     */
    private void genKey(T t) {
        Bean4DbUtil.genKey(t);
    }

    /**
     * 加密处理
     *
     * @param t 对象
     */
    private void encrypt(T t) {
        Bean4DbUtil.encrypt(t);
    }

    /**
     * 解密处理
     *
     * @param t 对象
     */
    private void decrypt(T t) {
        Bean4DbUtil.decrypt(t);
    }


    private void decrypt(List<T> ts) {
        if (null != ts && !ts.isEmpty()) {
            for (T t : ts) {
                decrypt(t);
            }
        }
    }
}
