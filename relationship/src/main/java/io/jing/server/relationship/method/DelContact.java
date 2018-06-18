package io.jing.server.relationship.method;

import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.ContactDel;
import io.jing.server.relationship.dao.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/6/13 12:17
 */
@Component
public class DelContact implements Method<ContactDel> {
    @Autowired
    private ContactDao contactDao;
    @Override
    public Object action(ContactDel contactsDel) {
        String myId = ThreadLocalUtil.userId();
        String userId = contactsDel.getUserId();
        contactDao.delContacts(myId,userId);
        return null;
    }
}
