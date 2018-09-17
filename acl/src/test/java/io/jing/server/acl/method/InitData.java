package io.jing.server.acl.method;

import io.jing.server.App;
import io.jing.server.acl.bean.ResourceBean;
import io.jing.server.acl.constant.AclConstant;
import io.jing.server.acl.dao.ResourceDao;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class InitData implements AclConstant {

    @Autowired
    private ResourceDao resourceDao;

    @Test
    public void init(){
        List<String> list = Lists.newArrayList(
             "资源,RESOURCE,acl"

        );
        List<String> strings = Lists.newArrayList(
                "查询,QUERY",
                "新增,INSERT",
                "修改,UPDATE",
                "删除,DELETE"
        );

        List<ResourceBean> rs = list.stream().map(str ->{
            String[] ss = str.split(",");
            return ss;
        }).flatMap(s2 ->{
            return strings.stream().map(s -> {
                String[] ss = s.split(",");
                String name = s2[0]+ss[0];
                String code = (s2[1]+"_"+ss[1]).toUpperCase();
                String url = ("/"+s2[2]+"/"+s2[1]+"/"+ss[1]).toLowerCase();
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
        List<String> codes = rs.stream().map(ResourceBean::getCode).collect(Collectors.toList());
        List<Compare> compares = CompareUtil.newInstance()
                .field("code").in(codes).compares();
        List<ResourceBean> rs2 = resourceDao.query(compares);
        List<String> codes2 = rs2.stream().map(ResourceBean::getCode).collect(Collectors.toList());
        rs = rs.stream().filter(r-> !codes2.contains(r.getCode())).collect(Collectors.toList());
        resourceDao.batchInsert(rs);
    }
}

