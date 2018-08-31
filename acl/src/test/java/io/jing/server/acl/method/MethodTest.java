package io.jing.server.acl.method;

import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.client.util.ClientUtil;
import io.jing.server.query.bean.Q;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;

import java.util.List;

public class MethodTest {

    public static void main(String[] args) {
        Q q = new Q();
        q.getPage().setOrderBies(null);
        List<Compare> compares = CompareUtil.newInstance()
                .field("name").like("%1%")
                .compares();
        q.setCompares(compares);
        q.setBean("role");
        Token token = new Token();
        Req req = Req.builder().service("acl")
                .method("queryPage").paramObj(q).build();
        for (int i = 0; i < 10; i++) {
            ClientUtil.call(token,req);
        }

    }
}
