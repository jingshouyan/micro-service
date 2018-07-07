package io.jing.server.sms.method;

import com.google.common.collect.Maps;
import io.jing.base.exception.MicroServiceException;
import io.jing.server.method.Method;
import io.jing.server.sms.bean.LoginBean;
import io.jing.server.sms.bean.UserBean;
import io.jing.server.sms.constant.SmsCode;
import io.jing.server.sms.dao.UserDao;
import io.jing.server.sms.util.jwt.JwtUtil;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/7/4 15:18
 */
@Component
public class Login implements Method<LoginBean> {

    @Autowired
    private UserDao userDao;

    @Override
    public Object action(LoginBean loginBean){

        Optional<UserBean> userBeanOptional = userDao.findByUsername(loginBean.getEmail());
        if(!userBeanOptional.isPresent()){
            throw new MicroServiceException(SmsCode.ACCOUNT_NOT_EXIST);
        }
        UserBean userBean = userBeanOptional.get();
        boolean pwCheck = BCrypt.checkpw(loginBean.getPassword(),userBean.getPwHash());
        if(!pwCheck){
            throw new MicroServiceException(SmsCode.PASSWORD_WRONG);
        }
        String token = JwtUtil.token(userBean);
        Map<String,String> map = Maps.newHashMap();
        map.put("token",token);
        return map;
    }
}
