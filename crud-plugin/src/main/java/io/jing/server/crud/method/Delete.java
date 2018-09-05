package io.jing.server.crud.method;

import io.jing.server.crud.bean.D;
import io.jing.server.method.Method;
import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.dao.BaseDao;
import org.springframework.stereotype.Component;

@Component("plugin.delete")
public class Delete extends BaseCrud implements Method<D> {

    @Override
    public Object action(D d) {
        BaseDao<BaseBean> dao = dao(d.getBean());
        switch (d.getType()) {
            case TYPE_SINGLE:
                return dao.delete(d.getId());
            case TYPE_MULTIPLE:
                return dao.delete4List(d.getIds());
            default:
                throw new RuntimeException("unsupported delete type "+d.getType());
        }
    }
}
