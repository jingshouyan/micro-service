package io.jing.server.relationship.dao;

import io.jing.server.relationship.bean.ContactsBean;
import io.jing.util.jdbc.core.dao.BaseDao;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/12 18:29
 */
public interface ContactsDao extends BaseDao<ContactsBean> {

    List<ContactsBean> ListContacts(String myId,long revision,int size,boolean containDel);
    int addContacts(ContactsBean contactsBean);
    int delContacts(String myId,String userId);
}
