package io.jing.server.relationship.dao.impl;

import io.jing.server.db.helper.IdHelper;
import io.jing.server.relationship.bean.ContactBean;
import io.jing.server.relationship.constant.RelationshipConstant;
import io.jing.server.relationship.dao.ContactDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.db.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/12 20:21
 */
@Repository
public class ContactDaoImpl extends BaseDaoImpl<ContactBean> implements ContactDao {

    @Autowired
    private IdHelper idHelper;

    @Override
    public List<ContactBean> listContacts(String myId, long revision, int size, boolean containDel) {
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
        Page<ContactBean> page = new Page<>();
        page.setPageSize(size);
        page.addOrderBy("revision");
        return queryLimit(compares,page);
    }

    @Override
    public int addContacts(ContactBean contactsBean){
        String id = contactsBean.genId();
        contactsBean.forUndelete();
        int fetch = update(contactsBean);
        if(fetch==0){
            contactsBean.forCreate();
            fetch = insert(contactsBean);
        }
        return fetch;
    }

    @Override
    public int delContacts(String myId, String userId) {
        ContactBean contactsBean = new ContactBean();
        contactsBean.setMyId(myId);
        contactsBean.setUserId(userId);
        contactsBean.genId();
        contactsBean.setRevision(idHelper.genId(RelationshipConstant.ID_TYPE_CONTACT_REVISION));
        contactsBean.forDelete();
        return update(contactsBean);
    }
}
