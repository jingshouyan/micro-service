package io.jing.server.relationship.method;

import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.ContactsDel;
import io.jing.server.relationship.dao.ContactsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/6/13 12:17
 */
@Component
public class DelContacts implements Method<ContactsDel> {
    @Autowired
    private ContactsDao contactsDao;
    @Override
    public Object action(ContactsDel contactsDel) {
        String myId = ThreadLocalUtil.userId();
        String userId = contactsDel.getUserId();
        contactsDao.delContacts(myId,userId);
        return null;
    }
}
