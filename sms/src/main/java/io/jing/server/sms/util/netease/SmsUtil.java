package io.jing.server.sms.util.netease;

import com.google.common.collect.Maps;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import io.jing.base.util.json.JsonUtil;
import io.jing.server.sms.constant.SmsConstant;
import io.jing.server.sms.util.http.HttpUtil;
import io.jing.server.sms.util.http.Response;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @author jingshouyan
 * #date 2018/6/28 17:40
 */
public class SmsUtil implements SmsConstant {
    public static SmsResult sendCode(String mobile){
        String code = code();
        Map<String,String> params = Maps.newHashMap();
        params.put("mobile",mobile);
        params.put("authCode",code);
        Response response = HttpUtil.post(SEND_CODE_URL,params,headers());
        SmsResult result = new SmsResult();
        try{
            result = JsonUtil.toBean(response.getBody(),SmsResult.class);
        }catch (Exception e){
            result.setCode("ERR");
        }
        result.setObj(code);
        return result;
    }

    private static Map<String,String> headers(){
        String appKey = SMS_KEY;
        String appSecret = SMS_SECRET;
        String nonce = UUID.randomUUID().toString().toLowerCase().replace("-","");
        String curTime = String.valueOf(System.currentTimeMillis()/1000L);
        HashFunction sha1 = Hashing.sha1();
        String checkSum = sha1.hashString(appSecret+nonce+curTime,Charset.forName("utf-8")).toString();
        Map<String,String> headers = Maps.newHashMap();
        headers.put("AppKey",appKey);
        headers.put("Nonce",nonce);
        headers.put("CurTime",curTime);
        headers.put("CheckSum",checkSum);
        return headers;
    }

    private static String code(){
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < CODE_LEN; i++) {
            int j = r.nextInt(10);
            sb.append(j);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        sendCode("18552805073");
    }
}
