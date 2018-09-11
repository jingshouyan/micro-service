package io.jing.server.admin.dao.impl;

import io.jing.server.admin.bean.AdminTokenBean;
import io.jing.server.admin.dao.AdminTokenDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/11 13:23
 */
@Repository
public class AdminTokenDaoImpl extends BaseDaoImpl<AdminTokenBean> implements AdminTokenDao {

    @Override
    public List<AdminTokenBean> listUserToken(String userId, int clientType) {
        List<Compare> compares = CompareUtil.newInstance()
                .field("userId").eq(userId)
                .field("clientType").eq(clientType)
                .compares();
        return query(compares);
    }
}
