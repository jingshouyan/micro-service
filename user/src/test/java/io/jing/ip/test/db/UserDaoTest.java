package io.jing.ip.test.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.jing.server.App;
import io.jing.server.user.dao.UserDao;
import io.jing.server.user.bean.AccountBean;
import io.jing.server.user.bean.UserBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
        userBean.setName("张三");
        userBean.setUserType(1);
        AccountBean accountBean = new AccountBean();
        accountBean.setContactInfo("abcdef");
        accountBean.setStatus(1);
        userBean.setAccountBean(accountBean);
        List<AccountBean> accountBeans = Lists.newArrayList(accountBean);
        userBean.setAccountBeans(accountBeans);
        userBean.forCreate();
        AccountBean accountBean1 = new AccountBean();
        accountBean1.setContactInfo("wwww");
        accountBean1.setStatus(2);
        accountBean1.forCreate();
        accountBeans.add(accountBean1);
        Map<String,AccountBean> map = Maps.newHashMap();
        map.put("str",accountBean1);
        userBean.setMap(map);
        userBean.setDate(new Date());
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
