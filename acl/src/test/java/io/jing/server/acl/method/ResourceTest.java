package io.jing.server.acl.method;


import io.jing.base.bean.Empty;
import io.jing.base.util.json.JsonUtil;
import io.jing.server.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.ws.soap.Addressing;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class ResourceTest {

    @Autowired
    private MyResource myResource;

    @Test
    public void r(){

        Object obj = myResource.action(new Empty());
        String str = JsonUtil.toJsonString(obj);
        System.out.println(str);
    }
}
