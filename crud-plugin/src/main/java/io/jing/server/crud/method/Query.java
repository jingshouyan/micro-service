package io.jing.server.crud.method;

import io.jing.server.crud.bean.R;
import io.jing.server.method.Method;
import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.dao.BaseDao;
import org.springframework.stereotype.Component;


@Component("plugin.query")
public class Query extends BaseCrud implements Method<R> {


    @Override
    public Object action(R r) {
        BaseDao<BaseBean> dao = dao(r.getBean());
        switch (r.getType()){
            case TYPE_SINGLE:
                return dao.find(r.getId()).orElse(null);
            case TYPE_MULTIPLE:
                return dao.findByIds(r.getIds());
            case TYPE_LIST:
                return dao.query(r.getCompares());
            case TYPE_LIMIT:
                return dao.queryLimit(r.getCompares(), r.getPage());
            case TYPE_PAGE:
                return dao.query(r.getCompares(), r.getPage());
            default:
                throw new RuntimeException("unsupported crud type: "+r.getType());
        }
    }
}
