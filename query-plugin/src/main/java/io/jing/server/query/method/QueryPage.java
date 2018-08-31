package io.jing.server.query.method;

import io.jing.server.query.bean.Q;
import org.springframework.stereotype.Component;

@Component("plugin.queryPage")
public class QueryPage extends BaseQuery {

    @Override
    public Object action(Q q) {
        return dao(q).query(q.getCompares(),q.getPage());
    }
}
