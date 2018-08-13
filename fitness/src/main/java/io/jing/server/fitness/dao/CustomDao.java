package io.jing.server.fitness.dao;

import io.jing.server.fitness.bean.CustomBean;
import io.jing.util.jdbc.core.dao.BaseDao;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/8/12 15:29
 */
public interface CustomDao extends BaseDao<CustomBean> {

    /**
     * 根据 openId 获取客户信息
     * @param openId
     * @return
     */
    public Optional<CustomBean> findByOpenId(String openId);
}
