package io.jing.server.user.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.method.Method;
import io.jing.server.user.bean.UserBean;
import io.jing.server.user.bean.UserReg;
import io.jing.server.user.constant.UserCode;
import io.jing.server.user.dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/6/11 13:27
 */
@Component
public class RegUser implements Method<UserReg> {

    @Autowired
    private UserDao userDao;

    @Override
    public Object action(UserReg userReg) {
        Optional<UserBean> userBeanOptional = userDao.findByUsername(userReg.getUsername());
        if(userBeanOptional.isPresent()){
            throw new MicroServiceException(UserCode.USERNAME_IN_USE);
        }
        UserBean userBean = new UserBean();
        userBean.setUsername(userReg.getUsername());
        String salt = BCrypt.gensalt();
        String password = BCrypt.hashpw(userReg.getPassword(),salt);
        userBean.setPassword(password);
        userBean.setUserType(userReg.getUserType());
        userBean.forCreate();
        userDao.insert(userBean);
        return userBean;
    }
}
