package io.jing.server.user.method;

import io.jing.server.method.Method;
import io.jing.server.user.bean.UserGet;
import io.jing.server.user.dao.UserDao;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/16 16:36
 */
@Component
public class GetUser implements Method<UserGet> {

    @Autowired
    private UserDao userDao;

    @Override
    public Object action(UserGet userGet) {
        List<Compare> compares = CompareUtil.newInstance()
                .field("id").in(userGet.getIdList())
                .compares();
        return userDao.query(compares);
    }
}
