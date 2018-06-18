package io.jing.server.relationship.method;

import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.ContactAdd;
import io.jing.server.relationship.bean.ContactBean;
import io.jing.server.relationship.dao.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/6/13 11:08
 */
@Component
public class AddContact implements Method<ContactAdd> {

    @Autowired
    private ContactDao contactDao;

    @Override
    public Object action(ContactAdd contactsAdd) {
        ContactBean contactsBean = new ContactBean();
        String userId = ThreadLocalUtil.userId();
        contactsBean.setMyId(userId);
        contactsBean.setUserId(contactsAdd.getUserId());
        contactsBean.setType(contactsAdd.getType());
        contactsBean.setRemark(contactsAdd.getRemark());
        contactDao.addContacts(contactsBean);
        return contactsBean;
    }

}
