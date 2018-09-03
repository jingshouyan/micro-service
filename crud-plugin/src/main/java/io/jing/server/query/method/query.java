package io.jing.server.query.method;

import com.google.common.base.Preconditions;
import io.jing.server.method.Method;
import io.jing.server.query.bean.Q;
import io.jing.util.jdbc.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component("plugin.query")
public class query implements Method<Q> {

    @Autowired
    private ApplicationContext ctx;

    private BaseDao<?> dao(Q q){
        String daoImplName = q.getBean()+"DaoImpl";
        BaseDao<?> dao = ctx.getBean(daoImplName,BaseDao.class);
        Preconditions.checkNotNull(dao,q.getBean()+"DaoImpl not found.");
        return dao;
    }

    @Override
    public Object action(Q q) {
        BaseDao<?> dao = dao(q);
        switch (q.getType()){
            case "id":
                return dao.find(q.getId()).orElse(null);
            case "ids":
                return dao.findByIds(q.getIds());
            case "list":
                return dao.query(q.getCompares());
            case "limit":
                return dao.queryLimit(q.getCompares(),q.getPage());
            case "page":
                return dao.query(q.getCompares(),q.getPage());
            default:
                throw new RuntimeException("unsupported query type");
        }
    }
}
