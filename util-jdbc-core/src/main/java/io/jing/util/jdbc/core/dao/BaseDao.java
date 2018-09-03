package io.jing.util.jdbc.core.dao;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.Page;

import java.util.List;
import java.util.Optional;


/**
 * 基于 spring-jdbc 的curl 接口
 * @author jingshouyan
 * #date 2018/4/14 17:25
 */
public interface BaseDao<T extends BaseBean> {
    /**
     * 根据主键查询数据
     *
     * @param id 主键
     * @return null或单条数据
     */
    Optional<T> find(Object id);

    /**
     * 根据主键列表查询数据
     *
     * @param ids 主键列表
     * @return 数据列表
     */
    List<T> findByIds(List<?> ids);

    /**
     * 条件查询
     *
     * @param compares 条件
     * @return 结果集
     */
    List<T> query(List<Compare> compares);

    /**
     * 条件查询（取一页数据）
     *
     * @param compares 条件
     * @param page     页
     * @return 结果集
     */
    List<T> queryLimit(List<Compare> compares, Page<T> page);

    /**
     * 条件分页查询
     *
     * @param compares 条件
     * @param page     页
     * @return 页信息及数据
     */
    Page<T> query(List<Compare> compares, Page<T> page);

    /**
     * 条件计数
     *
     * @param compares 条件
     * @return 数量
     */
    int count(List<Compare> compares);

    /**
     * 数据插入
     *
     * @param t 数据对象
     * @return 影响行数
     */
    int insert(T t);

//    int insert(List<T> list);

    /**
     * 数据批量插入
     * 注：数据字段是否设值需要保持一致
     *
     * @param list 数据集合
     * @return 影响行数
     */
    int batchInsert(List<T> list);

    /**
     * 基于主键的数据更新
     *
     * @param t 数据对象
     * @return 影响行数
     */
    int update(T t);

    /**
     * 基于主键的数据批量更新
     * 注：数据字段是否设值需要保持一致
     *
     * @param list 数据集合
     * @return 影响行数
     */
    int batchUpdate(List<T> list);

    /**
     * 根据条件更新数据
     *
     * @param t        数据值存放位置（忽略主键）
     * @param compares 条件
     * @return 影响行数
     */
    int update(T t, List<Compare> compares);

    /**
     * 根据主键列表删除数据
     *
     * @param ids 主键列表
     * @return 影响行数
     */
    int delete4List(List<?> ids);

    /**
     * 根据主键列表删除数据
     *
     * @param id 主键列表
     * @return 影响行数
     */
    int delete(Object id);

    /**
     * 条件删除数据
     *
     * @param compares 条件
     * @return 影响行数
     */
    int delete4Batch(List<Compare> compares);

    /**
     * 创建表
     *
     * @return 影响行数
     */
    int createTable();

    /**
     * 删除表
     *
     * @return 影响行数
     */
    int dropTable();
}
