package io.jing.server.acl.method;

import io.jing.base.util.json.JsonUtil;
import io.jing.server.acl.bean.ResourceBean;

import java.net.URLClassLoader;

public class ObjectTest {

    private int a =99;
    {
        System.out.println(toString());
    }

    public ObjectTest(){
        a = 88;
        System.out.println("xxx");
    }

    {
        System.out.println(hashCode());
    }

    public static void main(String[] args) {
//        ObjectTest objectTest = new ObjectTest();
//        ObjectTest objectTest1 = new ObjectTest();
//        ObjectTest objectTest2 = new ObjectTest();
//        URLClassLoader classLoader;

        ResourceBean resourceBean = new ResourceBean();
        resourceBean.setName("资源更新");
        resourceBean.setCode("RESOURCE_UPDATE");
        resourceBean.setDescription("");
        resourceBean.setMethod("POST");
        resourceBean.setUri("/acl/resource/update");
        resourceBean.setType(1);
        resourceBean.setState(1);
        resourceBean.setLogout(false);
        resourceBean.setCreatedAt(0L);
        resourceBean.setUpdatedAt(0L);
        resourceBean.setDeletedAt(0L);

        String str = JsonUtil.toJsonString(resourceBean);

        System.out.println(str);

    }
}
