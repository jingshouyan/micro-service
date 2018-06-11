package io.jing.server.user.dao;

import io.jing.server.user.bean.UserBean;
import io.jing.util.jdbc.core.dao.BaseDao;

import java.util.Optional;

/**
 * @author jingshouyan
 * @date 2018/4/24 21:08
 */
public interface UserDao extends BaseDao<UserBean> {

    /**
     * 根据 username 获取 userBean
     * @param username
     * @return
     */
    Optional<UserBean> findByUsername(String username);
}
