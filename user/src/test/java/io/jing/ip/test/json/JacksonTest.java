package io.jing.ip.test.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.jing.base.thrift.ReqBean;
import io.jing.base.util.json.JsonUtil;
import io.jing.server.user.constant.UserConstant;
import io.jing.server.user.bean.AccountBean;
import io.jing.server.user.bean.UserBean;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TTupleProtocol;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * @author jingshouyan
 * @date 2018/5/4 10:08
 */
@Slf4j
public class JacksonTest implements UserConstant {

    public static void main(String[] args) throws Exception {
//        String str2 = "[{\"userId\":123,\"ticket\":\"1234\",\"ticket2\":\"123\"}]";
//        JsonNode jsonNode = JsonUtil.readTree(str2);
//
//        System.out.println(jsonNode);
//
//        Object  obj = JsonUtil.get(jsonNode,"[0].userId");
//
//        System.out.println(obj);
//
//        String s = JsonUtil.toJsonString("1");
//        System.out.println(s);
//        jsonNode = JsonUtil.readTree(s);
//        System.out.println(jsonNode);

        t4();

    }

    @SneakyThrows
    public static void t2(){
        ReqBean reqBean = new ReqBean();
        reqBean.setMethod("aaa");
        reqBean.setParam("阿斯蒂芬");
        String s = JsonUtil.toJsonString(reqBean);
        System.out.println(s);

        TSerializer serializer = new TSerializer(new TBinaryProtocol.Factory());
        s = serializer.toString(reqBean);
        System.out.println(s);
        serializer = new TSerializer(new TJSONProtocol.Factory());
        s = serializer.toString(reqBean);
        System.out.println(s);

        serializer = new TSerializer(new TTupleProtocol.Factory());
        s = serializer.toString(reqBean);
        System.out.println(s);
        serializer = new TSerializer(new TCompactProtocol.Factory());
        s = serializer.toString(reqBean);
        System.out.println(s);
    }

    public static void t4(){
        ReqBean reqBean = new ReqBean();
        reqBean.setMethod("aaa");
        reqBean.setParam("阿\n斯\"蒂\t芬\\");
        String s = JsonUtil.toJsonString(reqBean);
        log.info(s);
        String pattern = "\"param\":\"[\\s\\S]*\"";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(s);
        while(m.find()){
            System.out.println(m.group());
        }
        String v = getStringValue(s,"param");
        System.out.println(v);
    }

    @SneakyThrows
    public static String getStringValue(String json,String key){
        key = "\""+key+"\":";
        int index = json.indexOf(key);
        if(index<0){
            return null;
        }
        index = index+ key.length();
        String str = json.substring(index);
        StringBuilder sb = new StringBuilder();

        char b1 = '\\';
        char b2 = '"';
        boolean start = false;
        boolean escape = false;
        for (int i = 0; i < str.length(); i++) {
            char b = str.charAt(i);
            if(start){
                if(b == b2 && !escape){
                    break;
                }
                if(escape){
                    b = escape(b);
                }
                escape = b == b1 && !escape;
                if(!escape) {
                    sb.append(b);
                }
            }else {
                start = b==b2;
            }
        }
        return sb.toString();
    }

    @SneakyThrows
    public static void desensitize(String json,String key){
        key = "\""+key+"\":";
        json.indexOf(key);

    }

    private static final Map<Character,Character> cMap = Optional.<Map<Character,Character>>empty().orElseGet(()->{
        Map<Character,Character> map = Maps.newHashMap();
        map.put('b','\b');
        map.put('f','\f');
        map.put('n','\n');
        map.put('r','\r');
        map.put('t','\t');
        return map;
    });
    public static char escape(char c){
        return cMap.getOrDefault(c,c);
    }

    /**
     * 测试 TSerializer 是否为线程安全
     */
    public static void t3(){
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        ReqBean reqBean = new ReqBean();
        reqBean.setMethod("aaa");
        reqBean.setParam("阿斯蒂芬");
        IntStream.rangeClosed(0,2000).forEach(i->{
            executorService.execute(()->{
                String s = JsonUtil.toJsonString(reqBean);
                log.info(s);
            });
        });

    }

    public static void t(){
        int i = 1000000;
        UserBean userBean = userBean();
        time();
        String s ="";
        for (int j = 0; j < i; j++) {
            s = JsonUtil.toJsonString(userBean);
        }
        System.out.println(s);
        time();

        for (int j = 0; j < i; j++) {
            JsonNode node = JsonUtil.valueToTree(userBean);
            ObjectNode node2 = (ObjectNode) node;
            node2.put("name","xxx");
            s = node.toString();
        }
        System.out.println(s);
        time();
    }

    private static long start = System.currentTimeMillis();

    public static void time(){
        long time = System.currentTimeMillis();
        System.out.println("use :"+(time-start));
        start = time;
    }

    public static UserBean userBean(){
        UserBean userBean = new UserBean();
        userBean.setName("张三");
        userBean.setUserType(1);
        AccountBean accountBean = new AccountBean();
        accountBean.setContactInfo("abcdef");
        accountBean.setStatus(1);
        userBean.setAccountBean(accountBean);
        List<AccountBean> accountBeans = Lists.newArrayList(accountBean);
        userBean.setAccountBeans(accountBeans);
        userBean.forCreate();
        AccountBean accountBean1 = new AccountBean();
        accountBean1.setContactInfo("wwww");
        accountBean1.setStatus(2);
        accountBean1.forCreate();
        accountBeans.add(accountBean1);
        return userBean;
    }
}
