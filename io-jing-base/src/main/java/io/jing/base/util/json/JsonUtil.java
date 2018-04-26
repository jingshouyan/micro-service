package io.jing.base.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.regex.Pattern;

/**
 * @author jingshouyan
 * @date 2018/4/26 10:20
 */
public class JsonUtil {
    /**
     * 获取 json 中 key 的值
     * @param json FastJSON 对象
     * @param key 含层级的key 例：cs.[0]?.b.name
     *            ?. 表示当前层级为null则返回null
     *            . 当前层级为null会抛出NPE
     * @return json 中的值
     */
    public static Object get(JSON json, String key){
        String pattern = "^\\[\\d]$";
        String[] keys = key.split("\\.");
        Object obj = json;
        for (int i = 0; i < keys.length; i++) {
            String str = keys[i];
            boolean nullable = false;
            if(str.endsWith("?")){
                nullable = true;
                str = str.substring(0,str.length()-1);
            }
            if(Pattern.matches(pattern,str)){
                int index = Integer.parseInt(str.substring(1,str.length()-1));
                JSONArray array = (JSONArray) obj;
                if(nullable && (null == array || i>=array.size())){
                    return null;
                }
                obj = array.get(index);
            }else {
                JSONObject jsonObject = (JSONObject) obj;
                obj = jsonObject.get(str);
            }
            if(nullable && obj==null){
                return null;
            }
        }
        return obj;
    }
}

