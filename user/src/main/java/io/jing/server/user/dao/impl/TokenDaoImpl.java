package io.jing.server.user.dao.impl;

import io.jing.server.user.bean.TokenBean;
import io.jing.server.user.dao.TokenDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * @author jingshouyan
 * #date 2018/6/11 13:23
 */
@Repository
public class TokenDaoImpl extends BaseDaoImpl<TokenBean> implements TokenDao {
}
