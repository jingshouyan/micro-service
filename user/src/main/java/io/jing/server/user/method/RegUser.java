package io.jing.server.user.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.db.helper.IdHelper;
import io.jing.server.method.Method;
import io.jing.server.user.bean.UserBean;
import io.jing.server.user.bean.UserReg;
import io.jing.server.user.constant.UserCode;
import io.jing.server.user.constant.UserConstant;
import io.jing.server.user.dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/6/11 13:27
 */
@Component("user.regUser")
public class RegUser implements Method<UserReg> {

    @Autowired
    private UserDao userDao;

//    @Autowired
//    private IdHelper idHelper;

    @Override
    public Object action(UserReg userReg) {
        Optional<UserBean> userBeanOptional = userDao.findByUsername(userReg.getUsername());
        if(userBeanOptional.isPresent()){
            throw new MicroServiceException(UserCode.USERNAME_IN_USE);
        }
        UserBean userBean = new UserBean();
//        userBean.setId(idHelper.genIdStr(UserConstant.ID_TYPE_USER));
        userBean.setUsername(userReg.getUsername());
        String salt = BCrypt.gensalt();
        String pwHash = BCrypt.hashpw(userReg.getPassword(),salt);
        userBean.setPwHash(pwHash);
        userBean.setUserType(userReg.getUserType());
        userBean.setNickname(userReg.getNickname());
        userBean.setIcon(userReg.getIcon());
        userBean.forCreate();
        userDao.insert(userBean);
        userBean.setPwHash(null);
        return userBean;
    }
}
