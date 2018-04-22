package io.jing.server.user.method;

import io.jing.base.bean.Empty;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.user.method.param.UserBean;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * @date 2018/4/18 20:45
 */
@Component
public class MUser implements Method<UserBean> {
    @Override
    public Object action(UserBean userBean) {
        return userBean;
    }
}
