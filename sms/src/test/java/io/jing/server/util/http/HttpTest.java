package io.jing.server.util.http;

import com.google.common.collect.Maps;
import io.jing.server.sms.util.http.HttpUtil;
import io.jing.server.sms.util.http.Response;

import java.util.Map;

public class HttpTest {

    public static void main(String[] args) {
        String url = "http://127.0.0.1:9000/api/upload";
        Map<String,String> files = Maps.newConcurrentMap();
        files.put("file","d:/avatar.jpg");
        Response response = HttpUtil.upload(url,files);
        System.out.println(response);
//
//        System.out.println(response);

//        File file = new File("./upload/");
//        System.out.println(file.getAbsolutePath());
//        if(!file.exists()){
//            file.mkdirs();
//        }
    }
}
