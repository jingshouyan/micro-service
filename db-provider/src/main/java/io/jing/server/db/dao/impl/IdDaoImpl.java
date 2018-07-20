package io.jing.server.db.dao.impl;

import io.jing.server.db.bean.IdBean;
import io.jing.server.db.dao.IdDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class IdDaoImpl extends BaseDaoImpl<IdBean> implements IdDao {
}
