package io.jing.server.admin.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.admin.bean.AdminBean;
import io.jing.server.method.Method;
import io.jing.server.admin.bean.AdminTokenBean;
import io.jing.server.admin.bean.LoginQ;
import io.jing.server.admin.constant.AdminCode;
import io.jing.server.admin.dao.AdminTokenDao;
import io.jing.server.admin.dao.AdminDao;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jingshouyan
 * #date 2018/6/11 19:56
 */
@Component("admin.login")
public class Login implements Method<LoginQ> {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AdminTokenDao adminTokenDao;

    @Override
    public Object action(LoginQ loginQ) {
        Optional<AdminBean> userBeanOptional = adminDao.findByUsername(loginQ.getUsername());
        if(!userBeanOptional.isPresent()){
            throw new MicroServiceException(AdminCode.USER_UNREGISTER);
        }
        AdminBean adminBean = userBeanOptional.get();
        boolean pwCheck = BCrypt.checkpw(loginQ.getPassword(), adminBean.getPwHash());
        if(!pwCheck){
            throw new MicroServiceException(AdminCode.PASSWORD_WRONG);
        }
        List<AdminTokenBean> adminTokenBeanList = adminTokenDao.listUserToken(adminBean.getId(), loginQ.getClientType());
        if(!adminTokenBeanList.isEmpty()){
            List<String> tokenList = adminTokenBeanList.stream().map(AdminTokenBean::getTicket).collect(Collectors.toList());
            adminTokenDao.delete4List(tokenList);
        }
        AdminTokenBean adminTokenBean = new AdminTokenBean();
        adminTokenBean.setTicket(UUID.randomUUID().toString());
        adminTokenBean.setUserId(adminBean.getId());
        adminTokenBean.setUserType(adminBean.getUserType());
        adminTokenBean.setClientType(loginQ.getClientType());
        adminTokenBean.forCreate();
        adminTokenDao.insert(adminTokenBean);
        return adminTokenBean;
    }
}
