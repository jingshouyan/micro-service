package io.jing.server.method.factory;

import io.jing.server.method.Method;
import io.jing.server.method.TestMethod;

/**
 * @author jingshouyan
 * @date 2018/4/15 15:02
 */
public class FactoryTest {
    public static void main(String[] args) {
        try{
            TestMethod testMethod = new TestMethod();
            MethodFactory.register("TestMethod",testMethod);
            Method method = MethodFactory.getMethod("TestMethod");
            System.out.println(method);
            method = MethodFactory.getMethod("abc");
            System.out.println(111);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
