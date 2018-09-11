package io.jing.server.admin.dao.impl;

import io.jing.server.admin.bean.AdminBean;
import io.jing.server.admin.dao.AdminDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author jingshouyan
 * @date 2018/4/24 21:09
 */
@Repository
public class AdminDaoImpl extends BaseDaoImpl<AdminBean> implements AdminDao {

    @Override
    public Optional<AdminBean> findByUsername(String username) {
        List<Compare> compares = CompareUtil.newInstance()
                .field("username").eq(username).compares();
        List<AdminBean> adminBeanList = query(compares);
        return adminBeanList.stream().findFirst();

    }
}
