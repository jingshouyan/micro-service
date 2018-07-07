package io.jing.server.sms.dao;

import io.jing.server.sms.bean.UserBean;
import io.jing.util.jdbc.core.dao.BaseDao;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/7/4 15:12
 */
public interface UserDao extends BaseDao<UserBean> {
    Optional<UserBean> findByUsername(String username);
}
