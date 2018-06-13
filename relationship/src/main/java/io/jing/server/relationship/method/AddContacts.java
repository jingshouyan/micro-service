package io.jing.server.relationship.method;

import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.ContactsAdd;
import io.jing.server.relationship.bean.ContactsBean;
import io.jing.server.relationship.dao.ContactsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/6/13 11:08
 */
@Component
public class AddContacts implements Method<ContactsAdd> {

    @Autowired
    private ContactsDao contactsDao;

    @Override
    public Object action(ContactsAdd contactsAdd) {
        ContactsBean contactsBean = new ContactsBean();
        String userId = ThreadLocalUtil.userId();
        contactsBean.setMyId(userId);
        contactsBean.setUserId(contactsAdd.getUserId());
        contactsBean.setType(contactsAdd.getType());
        contactsBean.setRemark(contactsAdd.getRemark());
        contactsDao.addContacts(contactsBean);
        return contactsBean;
    }

}
