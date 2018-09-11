package io.jing.server.admin.method;

import io.jing.base.bean.Empty;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.admin.dao.AdminTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/6/11 20:39
 */
@Component("admin.logout")
public class Logout implements Method<Empty> {

    @Autowired
    private AdminTokenDao adminTokenDao;

    @Override
    public Object action(Empty empty) {
        String ticket = ThreadLocalUtil.ticket();
        adminTokenDao.delete(ticket);
        return null;
    }
}
