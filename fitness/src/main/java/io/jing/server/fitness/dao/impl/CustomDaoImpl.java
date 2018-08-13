package io.jing.server.fitness.dao.impl;

import io.jing.server.fitness.bean.CustomBean;
import io.jing.server.fitness.dao.CustomDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;

import java.util.List;
import java.util.Optional;

/**
 * @author jingshouyan
 * #date 2018/8/12 15:31
 */
public class CustomDaoImpl extends BaseDaoImpl<CustomBean> implements CustomDao {

    @Override
    public Optional<CustomBean> findByOpenId(String openId){
        List<Compare> compares = CompareUtil.newInstance()
                .field("openId").eq(openId)
                .compares();
        List<CustomBean> customBeans = this.query(compares);
        return customBeans.stream().findFirst();
    }
}
