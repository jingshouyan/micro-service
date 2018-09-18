package io.jing.server.init;

import io.jing.server.App;
import io.jing.server.acl.bean.ResourceBean;
import io.jing.server.acl.constant.AclConstant;
import io.jing.server.acl.dao.ResourceDao;
import io.jing.server.method.Method;
import io.jing.util.jdbc.core.util.bean.StringUtil;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class InitAcl implements AclConstant {

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private ApplicationContext ctx;

    @Test
    public void init(){
        List<String> list = Lists.newArrayList(
                "资源,RESOURCE,acl",
                "角色,ROLE,acl",
                "用户角色,USER_ROLE,acl",
                "管理员,ADMIN,admin",
                "用户,USER,user"

        );
        List<String> strings = Lists.newArrayList(
                "查询,query",
                "新增,insert",
                "修改,update",
                "删除,delete"
        );



        final List<ResourceBean> rs = list.stream().map(str ->{
            String[] ss = str.split(",");
            return ss;
        }).flatMap(s2 ->{
            return strings.stream().map(s -> {
                String[] ss = s.split(",");
                String name = s2[0]+ss[0];
                String code = (s2[1]+"_"+ss[1]).toUpperCase();
                String url = ("/"+s2[2]+"/"+StringUtil.underline2Camel(s2[1],true)+"/"+ss[1]);
                ResourceBean r = new ResourceBean();
                r.setCode(code);
                r.setDescription("");
                r.setLogout(false);
                r.setMethod("POST");
                r.setName(name);
                r.setState(STATE_ENABLE);
                r.setType(RESOURCE_TYPE_PUB);
                r.setUri(url);
                return r;
            });
        }).collect(Collectors.toList());

        ctx.getBeansOfType(Method.class).keySet().forEach(key ->{
            String[] ss = key.split("\\.");
            if(!"plugin".equals(ss[0])){
                String code = ss[0].toUpperCase()+"_"+StringUtil.camel2Underline(ss[1]);
                String name = key;
                String url = "/"+ss[0]+"/"+ss[1];
                ResourceBean r = new ResourceBean();
                r.setCode(code);
                r.setDescription("");
                r.setLogout(false);
                r.setMethod("POST");
                r.setName(name);
                r.setState(STATE_ENABLE);
                r.setType(RESOURCE_TYPE_PUB);
                r.setUri(url);
                rs.add(r);
            }
        });

        List<String> codes = rs.stream().map(ResourceBean::getCode).collect(Collectors.toList());
        List<Compare> compares = CompareUtil.newInstance()
                .field("code").in(codes).compares();
        List<ResourceBean> rs2 = resourceDao.query(compares);
        List<String> codes2 = rs2.stream().map(ResourceBean::getCode).collect(Collectors.toList());
        List<ResourceBean> rs3 = rs.stream().filter(r-> !codes2.contains(r.getCode())).collect(Collectors.toList());
        resourceDao.batchInsert(rs3);
    }

    public static void main(String[] args) {
        String str = "user.query";
        String[] ss = str.split("\\.");
        System.out.println(ss);
    }
}

