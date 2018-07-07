package io.jing.server.user.method;

import io.jing.base.bean.Empty;
import io.jing.base.bean.Token;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.user.dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jingshouyan
 * #date 2018/6/11 20:39
 */
public class Logout implements Method<Empty> {

    @Autowired
    private TokenDao tokenDao;

    @Override
    public Object action(Empty empty) {
        String ticket = ThreadLocalUtil.ticket();
        tokenDao.delete(ticket);
        return null;
    }
}
