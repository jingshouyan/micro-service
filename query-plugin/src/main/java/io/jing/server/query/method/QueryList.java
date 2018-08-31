package io.jing.server.query.method;

import io.jing.server.query.bean.Q;
import org.springframework.stereotype.Component;

@Component("plugin.queryList")
public class QueryList extends BaseQuery {

    @Override
    public Object action(Q q) {
        return dao(q).query(q.getCompares());
    }
}
