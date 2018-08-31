package io.jing.server.acl.method;

import io.jing.server.acl.bean.ResourceBean;
import io.jing.server.acl.bean.RsQ;
import io.jing.server.acl.dao.ResourceDao;
import io.jing.server.method.Method;
import io.jing.util.jdbc.core.util.db.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchResource implements Method<RsQ> {

    @Autowired
    ResourceDao resourceDao;

    @Override
    public Object action(RsQ rsQ) {
        Page<ResourceBean> page = new Page<>();
        page.setPage(rsQ.getPage());
        page.setPageSize(rsQ.getSize());

        return null;
    }
}
