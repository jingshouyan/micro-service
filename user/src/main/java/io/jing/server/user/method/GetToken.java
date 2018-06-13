package io.jing.server.user.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.method.Method;
import io.jing.server.user.bean.TokenBean;
import io.jing.server.user.bean.TokenGet;
import io.jing.server.user.constant.UserCode;
import io.jing.server.user.dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/6/13 14:52
 */
@Component
public class GetToken implements Method<TokenGet> {

    @Autowired
    private TokenDao tokenDao;
    @Override
    public Object action(TokenGet tokenGet) {
        Optional<TokenBean> tokenBeanOptional = tokenDao.find(tokenGet.getTicket());
        return tokenBeanOptional.orElseThrow(()->new MicroServiceException(UserCode.TICKET_INVALID));
    }
}
