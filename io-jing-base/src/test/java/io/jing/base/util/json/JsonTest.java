//package io.jing.base.util.json;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Lists;
//import lombok.Data;
//
//import java.util.List;
//
///**
// * @author jingshouyan
// * @date 2018/4/26 15:13
// */
//public class JsonTest {
//
//
//
//    public static void main(String[] args) {
//        A a = new A();
//        a.setName("aaa");
//        B b1 = new B();
//        b1.setName("a.b");
//        a.setB(b1);
//        List<C> cs = Lists.newArrayList();
//        C c = new C();
//        c.setName("a.c");
//        B b2 = new B();
//        b2.setName("a.c.b");
//        c.setB(b2);
//        cs.add(c);
//        a.setCs(cs);
//        JSONObject jsonObject = (JSONObject)JSON.toJSON(a);
//        System.out.println(jsonObject);
//        System.out.println(jsonObject.get("b.name"));
//        System.out.println(JsonUtil.get(jsonObject,"cs.[1].c?.name"));
//        System.out.println(JsonUtil.get(jsonObject,"b.name"));
//
//        JSONArray array = new JSONArray();
//        array.add(123);
//        array.add(jsonObject);
//        array.add(a);
//        JsonUtil.get(array,"[0]");
//        JsonUtil.get(array,"[1].cs.[0].b.name");
////        get(array,"[2].cs.[0].b.name");
//        String str1 = JSON.toJSONString(123111111111L);
//        System.out.println(str1);
//        Object obj = JSON.parse(str1);
//        System.out.println(obj);
////        JSONObject j1 = JSON.()
//    }
//
//
//
//
//    @Data
//    public static class A extends D{
//        public A(){
//            super();
//        }
//        private String name;
//        private B b;
//        private List<C> cs;
//
//        @Override
//        public void init() {
//            System.out.println("init");
//        }
//    }
//
//    @Data
//    public static class B {
//        private String name;
//    }
//
//    @Data
//    public static class C {
//        private String name;
//        private B b;
//    }
//
//    public static abstract class D {
//        public D(){
//            init();
//        }
//        public void init(){
//            System.out.println("d init");
//        }
//    }
//
//}
