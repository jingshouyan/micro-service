package io.jing.server.relationship.method;

import com.sun.corba.se.pept.transport.ListenerThread;
import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.code.Code;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.client.util.ClientUtil;
import io.jing.server.db.helper.IdHelper;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.ContactAdd;
import io.jing.server.relationship.bean.ContactBean;
import io.jing.server.relationship.bean.UserBean;
import io.jing.server.relationship.constant.RelationshipConstant;
import io.jing.server.relationship.dao.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/13 11:08
 */
@Component("relationship.addContact")
public class AddContact implements Method<ContactAdd> {

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private IdHelper idHelper;

    @Override
    public Object action(ContactAdd contactAdd) {
        UserBean userBean = getUser(contactAdd.getUserId());
        ContactBean contactBean = new ContactBean();
        contactBean.setNickname(userBean.getNickname());
        contactBean.setIcon(userBean.getIcon());
        String userId = ThreadLocalUtil.userId();
        contactBean.setMyId(userId);
        contactBean.setUserId(contactAdd.getUserId());
        contactBean.setType(contactAdd.getType());
        contactBean.setRemark(contactAdd.getRemark());
        long revision = idHelper.genId(RelationshipConstant.ID_TYPE_CONTACT_REVISION);
        contactBean.setRevision(revision);
        contactDao.addContacts(contactBean);
        return contactBean;
    }

    private UserBean getUser(String id){
        Token token = ThreadLocalUtil.getToken();
        Req req = Req.builder().service("user").method("GetUser").param("{\"ids\":[\""+id+"\"]}").build();
        Rsp rsp = ClientUtil.call(token,req);
        if(rsp.getCode() == Code.SUCCESS){
            List<UserBean> userBeanList = rsp.list(UserBean.class);
            return userBeanList.get(0);
        }
        throw new MicroServiceException(rsp.getCode(),rsp.getMessage());
    }

}
