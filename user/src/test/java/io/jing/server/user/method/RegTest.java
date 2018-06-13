package io.jing.server.user.method;

import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.client.util.ClientUtil;
import io.jing.server.user.bean.TokenBean;
import io.jing.server.user.bean.UserLogin;
import io.jing.server.user.bean.UserReg;

/**
 * @author jingshouyan
 * #date 2018/6/11 18:47
 */
public class RegTest {

    public static void main(String[] args) {
        Token token = new Token();
        token.setUserId("123");
        token.setTicket("123ttt");
        token.setClientType(1);
        Req req = new Req();
        UserReg userReg = new UserReg();
        userReg.setUsername("WWE_ERE");
        userReg.setPassword("123");
//        userReg.setUserType();
        req.setParamObj(userReg);
        req.setService("user");
        req.setMethod("RegUser");
        Rsp rsp = ClientUtil.call(token,req);
        System.out.println(rsp);
        login("WWE_ERE","123");
    }

    private static TokenBean login(String username,String password){
        Token token = new Token();
        token.setUserId("123");
        token.setTicket("123ttt");
        token.setClientType(1);
        Req req = new Req();
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername(username);
        userLogin.setPassword(password);
        userLogin.setClientType(1);
//        userReg.setUserType();
        req.setParamObj(userLogin);
        req.setService("user");
        req.setMethod("Login");
        Rsp rsp = ClientUtil.call(token,req);
        System.out.println(rsp);
        return rsp.get(TokenBean.class);
    }
}