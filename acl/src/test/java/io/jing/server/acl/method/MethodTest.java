package io.jing.server.acl.method;

import com.google.common.collect.Maps;
import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.util.json.JsonUtil;
import io.jing.client.util.ClientUtil;
import io.jing.server.acl.bean.RoleBean;
import io.jing.server.crud.bean.C;
import io.jing.server.crud.bean.D;
import io.jing.server.crud.bean.R;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.db.Page;
import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodTest {

    public static void main(String[] args) {

//        q();
//        i();
        d();
    }

    public static void d(){
        D d = new D();
        d.setBean("role");
        d.setType("id");
        d.setId(243422257811030017L);
        d.setIds(new ArrayList<>());
        Token token = new Token();
        Req req = Req.builder().service("acl")
                .method("delete").paramObj(d).build();
        ClientUtil.call(token,req);
    }

    public static void i(){
        RoleBean role = new RoleBean();
        role.setName("test221");
        role.setDescription("test tst");
        role.setAll(true);
        role.setResourceIds(Lists.newArrayList(1L,2L,3L));
        List<RoleBean> list = Lists.newArrayList(role,role,role);
        C c = new C();
        c.setBean("role");
        c.setType("single");
        c.setData(JsonUtil.toJsonString(role));




        Token token = new Token();
        Req req = Req.builder().service("acl")
                .method("insert").paramObj(c).build();
        ClientUtil.call(token,req);
    }

    public static void q(){
        R r = new R();
        r.getPage().setOrderBies(null);
        List<Compare> compares = CompareUtil.newInstance()
                .field("name").like("%1%")
                .compares();
//        r.setCompares(compares);
        r.setBean("role");
        r.setType("list");
        r.setId(243422257811030017L);
        Token token = new Token();
        Req req = Req.builder().service("acl")
                .method("query").paramObj(r).build();
        for (int i = 0; i < 1; i++) {
            Rsp rsp = ClientUtil.call(token,req);
            List<RoleBean> list = rsp.list(RoleBean.class);
            System.out.println(list.size());
        }
    }
}
