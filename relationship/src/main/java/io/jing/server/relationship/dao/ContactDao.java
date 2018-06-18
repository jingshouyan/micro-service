package io.jing.server.relationship.dao;

import io.jing.server.relationship.bean.ContactBean;
import io.jing.util.jdbc.core.dao.BaseDao;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/12 18:29
 */
public interface ContactDao extends BaseDao<ContactBean> {

    List<ContactBean> ListContacts(String myId, long revision, int size, boolean containDel);
    int addContacts(ContactBean contactsBean);
    int delContacts(String myId,String userId);
}
