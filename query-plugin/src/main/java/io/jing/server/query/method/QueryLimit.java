package io.jing.server.query.method;

import io.jing.server.query.bean.Q;
import io.jing.util.jdbc.core.util.db.Page;
import org.springframework.stereotype.Component;

@Component("plugin.queryLimit")
public class QueryLimit extends BaseQuery {

    @Override
    public Object action(Q q) {
        return dao(q).queryLimit(q.getCompares(),q.getPage());
    }
}
