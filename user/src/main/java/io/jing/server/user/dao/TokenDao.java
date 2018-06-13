package io.jing.server.user.dao;

import io.jing.server.user.bean.TokenBean;
import io.jing.util.jdbc.core.dao.BaseDao;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/11 11:23
 */
public interface TokenDao extends BaseDao<TokenBean> {
    List<TokenBean> listUserToken(String userId,int clientType);
}
