package io.jing.server.user.dao.impl;

import io.jing.server.user.bean.UserBean;
import io.jing.server.user.dao.UserDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author jingshouyan
 * @date 2018/4/24 21:09
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<UserBean> implements UserDao {

    @Override
    public Optional<UserBean> findByUsername(String username) {
        List<Compare> compares = CompareUtil.newInstance()
                .field("username").eq(username).compares();
        List<UserBean> userBeanList = query(compares);
        return userBeanList.stream().findFirst();

    }
}
