package io.jing.server.relationship.dao.impl;

import io.jing.server.relationship.bean.ContactsBean;
import io.jing.server.relationship.dao.ContactsDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.db.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/12 20:21
 */
@Repository
public class ContactsDaoImpl extends BaseDaoImpl<ContactsBean> implements ContactsDao {
    @Override
    public List<ContactsBean> ListContacts(String myId, long revision,int size, boolean containDel) {
        List<Compare> compares = CompareUtil.newInstance()
                .field("myId").eq(myId)
                .field("revision").gt(revision)
                .compares();
        if(!containDel){
            Compare c = new Compare();
            c.setField("deletedAt");
            c.setEq(-1);
            compares.add(c);
        }
        Page<ContactsBean> page = new Page<>();
        page.setPageSize(size);
        page.addOrderBy("revision");
        return queryLimit(compares,page);
    }

    @Override
    public int addContacts(ContactsBean contactsBean){
        String id = contactsBean.genId();
        contactsBean.forUpdate();
        int fetch = update(contactsBean);
        if(fetch==0){
            contactsBean.forCreate();
            fetch = insert(contactsBean);
        }
        return fetch;
    }

    @Override
    public int delContacts(String myId, String userId) {
        ContactsBean contactsBean = new ContactsBean();
        contactsBean.setMyId(myId);
        contactsBean.setUserId(userId);
        contactsBean.genId();
        contactsBean.forDelete();
        return update(contactsBean);
    }
}
