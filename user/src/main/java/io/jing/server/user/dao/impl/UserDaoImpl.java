package io.jing.server.user.dao.impl;

import io.jing.server.user.bean.UserBean;
import io.jing.server.user.dao.UserDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * @author jingshouyan
 * @date 2018/4/24 21:09
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<UserBean> implements UserDao {
}
