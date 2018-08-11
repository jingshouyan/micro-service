package io.jing.server.user.method;

import io.jing.server.method.Method;
import io.jing.server.user.bean.UserBean;
import io.jing.server.user.bean.UserEdit;
import io.jing.server.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/8/9 22:18
 */
@Component("user.editUser")
public class EditUser implements Method<UserEdit> {

    @Autowired
    private UserDao userDao;
    @Override
    public Object action(UserEdit userEdit) {
        UserBean user = new UserBean();
        user.setId(userEdit.getUserId());
        user.setIcon(userEdit.getIcon());
        user.setNickname(userEdit.getNickname());
        user.forUpdate();
        return userDao.update(user);
    }
}
