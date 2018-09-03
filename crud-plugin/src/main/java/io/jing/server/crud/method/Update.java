package io.jing.server.crud.method;

import io.jing.base.util.json.JsonUtil;
import io.jing.server.crud.bean.U;
import io.jing.server.method.Method;
import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.dao.BaseDao;
import org.springframework.stereotype.Component;

@Component("plugin.update")
public class Update extends BaseCrud implements Method<U> {

    @Override
    public Object action(U u) {
        BaseDao<BaseBean> dao = dao(u.getBean());
        Class<BaseBean> clazz = dao.getClazz();
        switch (u.getType()){
            case TYPE_SINGLE:
                return dao.update(JsonUtil.toBean(u.getData(),clazz));
            case TYPE_MULTIPLE:
                return dao.batchUpdate(JsonUtil.toList(u.getData(),clazz));
            default:
                throw new RuntimeException("unsupported insert type: " + u.getType());
        }
    }
}