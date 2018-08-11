package io.jing.server.user.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.method.Method;
import io.jing.server.user.bean.TokenBean;
import io.jing.server.user.bean.UserBean;
import io.jing.server.user.bean.UserLogin;
import io.jing.server.user.constant.UserCode;
import io.jing.server.user.dao.TokenDao;
import io.jing.server.user.dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jingshouyan
 * #date 2018/6/11 19:56
 */
@Component("user.login")
public class Login implements Method<UserLogin> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;

    @Override
    public Object action(UserLogin userLogin) {
        Optional<UserBean> userBeanOptional = userDao.findByUsername(userLogin.getUsername());
        if(!userBeanOptional.isPresent()){
            throw new MicroServiceException(UserCode.USER_UNREGISTER);
        }
        UserBean userBean = userBeanOptional.get();
        boolean pwCheck = BCrypt.checkpw(userLogin.getPassword(),userBean.getPwHash());
        if(!pwCheck){
            throw new MicroServiceException(UserCode.PASSWORD_WRONG);
        }
        List<TokenBean> tokenBeanList = tokenDao.listUserToken(userBean.getId(),userLogin.getClientType());
        if(!tokenBeanList.isEmpty()){
            List<String> tokenList = tokenBeanList.stream().map(TokenBean::getTicket).collect(Collectors.toList());
            tokenDao.delete4List(tokenList);
        }
        TokenBean tokenBean = new TokenBean();
        tokenBean.setTicket(UUID.randomUUID().toString());
        tokenBean.setUserId(userBean.getId());
        tokenBean.setUserType(userBean.getUserType());
        tokenBean.setClientType(userLogin.getClientType());
        tokenBean.forCreate();
        tokenDao.insert(tokenBean);
        return tokenBean;
    }
}
