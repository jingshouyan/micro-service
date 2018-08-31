package io.jing.server.acl.dao.impl;

import io.jing.server.acl.bean.UserRoleBean;
import io.jing.server.acl.dao.UserRoleDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleDaoImpl extends BaseDaoImpl<UserRoleBean> implements UserRoleDao {
}
