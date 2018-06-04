package io.jing.server.method;

import io.jing.base.bean.Rsp;
import io.jing.base.bean.ServiceInfo;
import io.jing.base.bean.Token;

/**
 * @author jingshouyan
 * @date 2018/4/15 1:02
 */
public class TestMethod implements Method<Token> {

    public static void main(String[] args) {
        TestMethod testMethod = new TestMethod();
        System.out.println(testMethod.getClazz());

        Method<ServiceInfo> method = (ServiceInfo serviceInfo) -> null;

        System.out.println(method.getClazz());
    }

    @Override
    public Rsp action(Token token) {
        return null;
    }

    //    @SuppressWarnings("unchecked")
//    default public Class<T> getClazz() {
//        Class<T> clazz = null;
//        Class<?> c = getClass();
//        Object o1 = c.getGenericSuperclass();
//        Object o2 = c.getSuperclass();
//        Object o3 = c.getAnnotatedInterfaces();
//        Object o4 = c.getAnnotatedSuperclass();
//        Object o5 = c.getClasses();
//        Object o6 = c.getComponentType();
//        Object o7 = c.getDeclaredClasses();
//        Object o8 = c.getEnclosingClass();
//        Object o9 = c.getGenericInterfaces();
//        Object o10 = c.getInterfaces();
//        Object o11 = c.getTypeParameters();
//        Type t = getClass().getGenericSuperclass();
//        if (t instanceof ParameterizedType) {
//            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
//            clazz = (Class<T>) p[0];
//        }
//        return clazz;
//    }
}
