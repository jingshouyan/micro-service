package io.jing.server.sms.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.method.Method;
import io.jing.server.sms.bean.UserBean;
import io.jing.server.sms.bean.UserReg;
import io.jing.server.sms.dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/7/4 17:07
 */
@Component
public class Register implements Method<UserReg> {

    @Autowired
    private UserDao userDao;
    @Override
    public Object action(UserReg userReg) {
        Optional<UserBean> userBeanOptional = userDao.findByUsername(userReg.getEmail());
        if(userBeanOptional.isPresent()){
            throw new MicroServiceException(1);
        }
        UserBean userBean = new UserBean();
        userBean.setUsername(userReg.getEmail());
        String salt = BCrypt.gensalt();
        String pwHash = BCrypt.hashpw(userReg.getPassword(),salt);
        userBean.setPwHash(pwHash);
        userBean.setUserType(userReg.getUserType());
        userBean.setNickname(userReg.getFullName());
        userBean.setIcon(userReg.getIcon());
        userBean.forCreate();
        userDao.insert(userBean);
        userBean.setPwHash(null);
        return userBean;
    }
}
