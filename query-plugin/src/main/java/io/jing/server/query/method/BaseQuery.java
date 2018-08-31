package io.jing.server.query.method;

import com.google.common.base.Preconditions;
import io.jing.server.method.Method;
import io.jing.server.query.bean.Q;
import io.jing.util.jdbc.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


public abstract class BaseQuery implements Method<Q> {

    @Autowired
    private ApplicationContext ctx;

    @Override
    public Class<Q> getClazz(){
        return Q.class;
    }

    protected BaseDao<?> dao(Q q){
        String daoImplName = q.getBean()+"DaoImpl";
        BaseDao<?> dao = ctx.getBean(daoImplName,BaseDao.class);
        Preconditions.checkNotNull(dao,q.getBean()+"DaoImpl not found.");
        return dao;
    }
}
