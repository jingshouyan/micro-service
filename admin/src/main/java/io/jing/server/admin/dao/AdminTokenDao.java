package io.jing.server.admin.dao;

import io.jing.server.admin.bean.AdminTokenBean;
import io.jing.util.jdbc.core.dao.BaseDao;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/11 11:23
 */
public interface AdminTokenDao extends BaseDao<AdminTokenBean> {
    List<AdminTokenBean> listUserToken(String userId, int clientType);
}
