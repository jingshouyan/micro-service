package io.jing.server.admin.method;

import io.jing.base.bean.Empty;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.admin.bean.AdminTokenBean;
import io.jing.server.admin.constant.AdminCode;
import io.jing.server.admin.dao.AdminTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/6/13 14:52
 */
@Component("admin.getToken")
public class GetToken implements Method<Empty> {

    @Autowired
    private AdminTokenDao adminTokenDao;
    @Override
    public Object action(Empty empty) {
        String ticket = ThreadLocalUtil.ticket();
        Optional<AdminTokenBean> tokenBeanOptional = adminTokenDao.find(ticket);
        return tokenBeanOptional.orElseThrow(()->new MicroServiceException(AdminCode.TICKET_INVALID));
    }
}
