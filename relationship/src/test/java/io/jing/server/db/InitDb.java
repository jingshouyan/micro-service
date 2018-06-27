package io.jing.server.db;

import io.jing.server.App;
import io.jing.util.jdbc.core.dao.BaseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author jingshouyan
 * @date 2018/4/24 21:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class InitDb {

    @Autowired
    private ApplicationContext ctx;


    @Test
    @Order(2)
    public void createTable() {
        Map<String, BaseDao> map = ctx.getBeansOfType(BaseDao.class);

        for (BaseDao dao : map.values()
                ) {
            dao.createTable();
        }
    }

    @Test
    @Order(1)
    public void dropTable() {
        Map<String, BaseDao> map = ctx.getBeansOfType(BaseDao.class);

        for (BaseDao dao : map.values()
                ) {
            dao.dropTable();
        }
    }
}
