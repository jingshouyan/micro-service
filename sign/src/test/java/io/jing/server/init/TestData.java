package io.jing.server.init;

import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.client.util.ClientUtil;
import io.jing.server.App;
import io.jing.server.relationship.bean.ContactAdd;
import io.jing.server.relationship.method.AddContact;
import io.jing.server.user.bean.UserReg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class TestData {
    static {
        System.setProperty("conf.ws.port","7890");
    }

    @Test
    public void reg() {
        for (int i = 10; i < 10000; i++) {
            Token token = new Token();
            token.setUserId("123");
            token.setTicket("123ttt");
            token.setClientType(1);
            Req req = new Req();
            UserReg userReg = new UserReg();
            userReg.setUsername("test"+i);
            userReg.setPassword("123456");
            userReg.setNickname("测试"+i);
            req.setParamObj(userReg);
            req.setService("user");
            req.setMethod("regUser");
            Rsp rsp = ClientUtil.call(token,req);
            System.out.println(rsp);
        }

    }

    @Autowired
    private AddContact addContact;

    @Test
    public void addContact(){
        Token token = Token.builder().userId("10001").ticket("123123").build();
        ThreadLocalUtil.setToken(token);
        for (int i = 12001; i < 18000; i++) {
            ContactAdd c = new ContactAdd();
            c.setUserId(""+i);
            c.setRemark("");
            c.setType(1);
            addContact.action(c);
        }

    }
}
