package io.jing.ip.test.db;

import com.google.common.collect.Lists;
import io.jing.server.user.bean.UserBean;
import io.jing.server.user.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author jingshouyan
 * @date 2018/4/24 21:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    @Order(1)
    public void insert(){
        UserBean userBean = new UserBean();

        userDao.insert(userBean);
    }

    @Test
    @Order(2)
    public void query(){
        List<UserBean> userBeans = userDao.query(Lists.newArrayList());
        for (UserBean u: userBeans) {
            System.out.println(u);
        }
    }
}
