package io.jing.server.admin.method;

import io.jing.server.admin.bean.AdminBean;
import io.jing.server.method.Method;
import io.jing.server.admin.bean.PwdQ;
import io.jing.server.admin.dao.AdminDao;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/8/9 21:32
 */
@Component("admin.resetPwd")
public class ResetPwd implements Method<PwdQ> {

    @Autowired
    private AdminDao adminDao;
    @Override
    public Object action(PwdQ pwdQ) {
        String salt = BCrypt.gensalt();
        String pwHash = BCrypt.hashpw(pwdQ.getPassword(),salt);
        AdminBean user = new AdminBean();
        user.setId(pwdQ.getUserId());
        user.setPwHash(pwHash);
        user.forUpdate();
        return adminDao.update(user);
    }
}
