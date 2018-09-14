package io.jing.server.util.http;

import io.jing.server.sms.bean.UserBean;

public class A {

    private static class B {
        private static  String c = "LUULKJ";
//        private static  String d = s();
//        private static String f ;
//
        static {
            System.out.println(c);
//            f = c;
        }

        private static String s(){
            System.out.println("sss");
            return "123dfer";
        }
    }

    public static String gets(){
        return B.c.toLowerCase();
    }

    public static void main(String[] args) {
//        String s = A.gets();
//        System.out.println(s);
//        UserBean userBean = new UserBean();
//        userBean.setId("");
//        userBean.setUsername("");
//        userBean.setPwHash("");
//        userBean.setNickname("");
//        userBean.setIcon("");
//        userBean.setUserType(0);
//        userBean.setCreatedAt(0L);
//        userBean.setUpdatedAt(0L);
//        userBean.setDeletedAt(0L);
        for (int i = 0; i < 10000; i++) {
            String.valueOf(i).intern();
        }
        System.out.println(123);

    }
}
