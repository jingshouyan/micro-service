package io.jing.server.json;

import io.jing.base.util.json.JsonUtil;
import io.jing.server.relationship.bean.UserBean;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/7/21 23:45
 */
public class JsonTest {

    public static void main(String[] args) {
        String str = "[{\"createdAt\":1532136237529,\"updatedAt\":1532136237529,\"deletedAt\":-1,\"id\":\"10001\",\"username\":\"jingle\",\"nickname\":\"呵呵\",\"icon\":\"\",\"userType\":1}]";
        List<UserBean> us = JsonUtil.toList(str,UserBean.class);
        System.out.println(us);
    }
}
