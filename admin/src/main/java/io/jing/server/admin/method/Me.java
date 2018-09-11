package io.jing.server.admin.method;

import io.jing.base.bean.Empty;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.admin.constant.AdminCode;
import io.jing.server.admin.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/7/6 23:14
 */
@Component("admin.me")
public class Me implements Method<Empty>{

    @Autowired
    private AdminDao adminDao;
    @Override
    public Object action(Empty empty) {
        String userId = ThreadLocalUtil.userId();
        return adminDao.find(userId)
                .orElseThrow(()-> new MicroServiceException(AdminCode.USER_NOT_FOUND));
    }
}
