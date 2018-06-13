package io.jing.server.relationship.method;

import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.method.Method;
import io.jing.server.relationship.bean.QueryParam;
import io.jing.server.relationship.dao.ContactsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/6/13 14:32
 */
@Component
public class ListContacts implements Method<QueryParam> {

    @Autowired
    private ContactsDao contactsDao;

    @Override
    public Object action(QueryParam queryParam) {
        String userId = ThreadLocalUtil.userId();
        return contactsDao.ListContacts(userId,queryParam.getRevision(),queryParam.getSize(),queryParam.isContainDel());
    }
}
