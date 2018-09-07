package io.jing.server.acl.method;

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
        ObjectTest objectTest = new ObjectTest();
        ObjectTest objectTest1 = new ObjectTest();
        ObjectTest objectTest2 = new ObjectTest();
        URLClassLoader classLoader;
    }
}
