package io.jing.server.acl.dao.impl;

import io.jing.server.acl.bean.RoleBean;
import io.jing.server.acl.dao.RoleDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<RoleBean> implements RoleDao {
}
