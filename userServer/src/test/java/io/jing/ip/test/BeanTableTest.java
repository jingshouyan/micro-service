package io.jing.ip.test;

import io.jing.server.user.model.UserBean;
import io.jing.util.jdbc.core.util.db.Bean4DbUtil;
import io.jing.util.jdbc.core.util.db.BeanTable;

/**
 * @author jingshouyan
 * @date 2018/4/24 20:01
 */
public class BeanTableTest {

    public static void main(String[] args) {
        BeanTable beanTable = Bean4DbUtil.getBeanTable(UserBean.class);
        System.out.println(beanTable);
    }
}
