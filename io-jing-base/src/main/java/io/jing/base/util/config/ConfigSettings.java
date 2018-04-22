package io.jing.base.util.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jingshouyan
 * @date 2018/4/16 12:20
 */
public class ConfigSettings {

    public static final String ENV_PREFIX = "CONF_";
    public static final String PROPERTY_PREFIX = "conf.";

    /**
     * 获取设置信息 优先级 1.java系统变量 2.环境变量 3.配置文件
     * java系统变量 key = "conf."+key
     * 环境变量     key = "CONF_"+key.replace('.','_').toUpperCase();
     * @param key key
     * @return optional.ofNullable(value)
     */
    public static Optional<String> get(String key){
        String value = System.getProperty(PROPERTY_PREFIX+key);
        if(value==null) {
            String envKey = ENV_PREFIX+key.replace('.','_').toUpperCase();
            value = System.getenv(envKey);
            if(value == null){
                value = CONFIG_MAP.get(key);
            }
        }
       return Optional.ofNullable(value);
    }

    private static final List<String> FILEPATH_LIST = Lists.newArrayList(
            "config/config.properties"
    );
    private static final Map<String,String> CONFIG_MAP = Maps.newHashMap();
    static {
        FILEPATH_LIST.forEach(filepath->CONFIG_MAP.putAll(load(filepath)));
    }


    private static Map<String,String> load(String configFilepath) {
        Map<String,String> map = Maps.newHashMap();
        try {
            Configuration config = new PropertiesConfiguration(configFilepath);
            System.out.println("load config [" + configFilepath + "] start...");
            Iterator<String> it = config.getKeys();
            while (it.hasNext()) {
                String key = it.next().trim();
                String value = config.getString(key).trim();
                map.put(key, value);
                String value2 = value;
                if (match(key) || match(value)) {
                    value2 = "****";
                }
                System.out.println("load config  " + String.format("%1$-15s", key) + " = " + value2);
            }
            System.out.println("load config [" + configFilepath + "] end");
        } catch (Exception e) {
            System.err.println("load config [" + configFilepath + "] error."+e.getMessage());
        }
        return map;
    }

    private static boolean match(String context) {
        return match(context, "pw|password");
    }

    private static boolean match(String context, String reg) {
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(context);
        return matcher.find();
    }

}
