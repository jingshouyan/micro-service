package io.jing.server.user.method;

import io.jing.server.method.Method;
import io.jing.server.user.bean.UserIds;
import io.jing.server.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/8/11 9:44
 */
@Component("user.delUser")
public class DelUser implements Method<UserIds> {

    @Autowired
    private UserDao userDao;
    @Override
    public Object action(UserIds userIds) {
        return userDao.delete4List(userIds.getIds());
    }
}
