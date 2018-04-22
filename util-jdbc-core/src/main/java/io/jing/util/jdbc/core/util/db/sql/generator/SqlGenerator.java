package io.jing.util.jdbc.core.util.db.sql.generator;

import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.Page;
import io.jing.util.jdbc.core.util.db.SqlPrepared;

import java.util.List;

/**
 * sql 生成器
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
public interface SqlGenerator<T> {

    /**
     * 条件查询语句生成
     * @param compares 条件
     * @return sql语句和参数
     */
    SqlPrepared query(List<Compare> compares);

    /**
     * 带分页的条件查询语句生成
     * @param compares 条件
     * @param page 分页信息
     * @return sql语句和参数
     */
    SqlPrepared query(List<Compare> compares, Page<T> page);

    /**
     *  条件查询计数语句生成
     * @param compares 条件
     * @return sql语句和参数
     */
    SqlPrepared count(List<Compare> compares);

    /**
     *  数据插入语句生成
     * @param beans 需要插入的对象
     * @return sql语句和参数
     */
    SqlPrepared insert(List<T> beans);

    /**
     * 数据更新语句生成
     * @param bean 需要更新的属性
     * @param compares 条件
     * @return sql语句和参数
     */
    SqlPrepared update(T bean, List<Compare> compares);

    /**
     * 条件删除语句生成
     * @param compares 条件
     * @return sql语句和参数
     */
    SqlPrepared delete(List<Compare> compares);

    /**
     * 建表语句生成
     * @return sql语句和参数
     */
    SqlPrepared createTableSql();

    /**
     *  删表语句生成
     * @return sql语句和参数
     */
    SqlPrepared dropTableSql();
}
