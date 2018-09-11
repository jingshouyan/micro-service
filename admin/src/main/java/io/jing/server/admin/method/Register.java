package io.jing.server.admin.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.admin.bean.AdminBean;
import io.jing.server.method.Method;
import io.jing.server.admin.bean.RegisterQ;
import io.jing.server.admin.constant.AdminCode;
import io.jing.server.admin.dao.AdminDao;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/6/11 13:27
 */
@Component("admin.register")
public class Register implements Method<RegisterQ> {

    @Autowired
    private AdminDao adminDao;

//    @Autowired
//    private IdHelper idHelper;

    @Override
    public Object action(RegisterQ registerQ) {
        Optional<AdminBean> userBeanOptional = adminDao.findByUsername(registerQ.getUsername());
        if(userBeanOptional.isPresent()){
            throw new MicroServiceException(AdminCode.USERNAME_IN_USE);
        }
        AdminBean adminBean = new AdminBean();
//        adminBean.setId(idHelper.genIdStr(AdminConstant.ID_TYPE_USER));
        adminBean.setUsername(registerQ.getUsername());
        String salt = BCrypt.gensalt();
        String pwHash = BCrypt.hashpw(registerQ.getPassword(),salt);
        adminBean.setPwHash(pwHash);
        adminBean.setUserType(registerQ.getUserType());
        adminBean.setNickname(registerQ.getNickname());
        adminBean.setIcon(registerQ.getIcon());
        adminBean.forCreate();
        adminDao.insert(adminBean);
        adminBean.setPwHash(null);
        return adminBean;
    }
}
