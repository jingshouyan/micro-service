package io.jing.server.crud.method;

import io.jing.base.util.json.JsonUtil;
import io.jing.server.crud.bean.C;
import io.jing.server.method.Method;
import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.dao.BaseDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("plugin.insert")
public class Insert extends BaseCrud implements Method<C> {

    @Override
    public Object action(C c) {
        BaseDao<BaseBean> dao = dao(c.getBean());
        Class<BaseBean> clazz = dao.getClazz();
        switch (c.getType()){
            case TYPE_SINGLE:
                BaseBean bean = JsonUtil.toBean(c.getData(),clazz);
                dao.insert(bean);
                return bean;
            case TYPE_MULTIPLE:
                List<BaseBean> list = JsonUtil.toList(c.getData(),clazz);
                dao.batchInsert(list);
                return list;
            default:
                throw new RuntimeException("unsupported insert type: "+c.getType());
        }
    }
}
