package io.jing.server.admin.dao;

import io.jing.server.admin.bean.AdminBean;
import io.jing.util.jdbc.core.dao.BaseDao;

import java.util.Optional;

/**
 * @author jingshouyan
 * @date 2018/4/24 21:08
 */
public interface AdminDao extends BaseDao<AdminBean> {

    /**
     * 根据 username 获取 userBean
     * @param username
     * @return
     */
    Optional<AdminBean> findByUsername(String username);
}
