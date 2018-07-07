package io.jing.server.user.method;

import io.jing.base.bean.Empty;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.user.constant.UserCode;
import io.jing.server.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/7/6 23:14
 */
@Component
public class Me implements Method<Empty>{

    @Autowired
    private UserDao userDao;
    @Override
    public Object action(Empty empty) {
        String userId = ThreadLocalUtil.userId();
        return userDao.find(userId)
                .orElseThrow(()-> new MicroServiceException(UserCode.UNSER_NOT_FOUND));
    }
}
