package io.jing.server.user.method;

import io.jing.server.method.Method;
import io.jing.server.user.bean.PwdReset;
import io.jing.server.user.bean.UserBean;
import io.jing.server.user.dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/8/9 21:32
 */
@Component("user.resetPwd")
public class ResetPwd implements Method<PwdReset> {

    @Autowired
    private UserDao userDao;
    @Override
    public Object action(PwdReset pwdReset) {
        String salt = BCrypt.gensalt();
        String pwHash = BCrypt.hashpw(pwdReset.getPassword(),salt);
        UserBean user = new UserBean();
        user.setId(pwdReset.getUserId());
        user.setPwHash(pwHash);
        user.forUpdate();
        return userDao.update(user);
    }
}
