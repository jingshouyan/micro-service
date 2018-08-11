package io.jing.server.user.method;

import io.jing.server.method.Method;
import io.jing.server.user.bean.UserIds;
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
@Component("user.getUser")
public class GetUser implements Method<UserIds> {

    @Autowired
    private UserDao userDao;

    @Override
    public Object action(UserIds userGet) {
        List<Compare> compares = CompareUtil.newInstance()
                .field("id").in(userGet.getIds())
                .compares();
        System.out.println(compares);
        return userDao.query(compares);
    }
}
