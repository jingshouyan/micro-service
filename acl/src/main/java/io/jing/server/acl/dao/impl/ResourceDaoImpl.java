package io.jing.server.acl.dao.impl;

import io.jing.server.acl.bean.ResourceBean;
import io.jing.server.acl.dao.ResourceDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceDaoImpl extends BaseDaoImpl<ResourceBean> implements ResourceDao {
}
