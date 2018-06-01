package io.jing.util.jdbc.core.util.bean;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 工具类
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
public class StringUtil {

    private static final String SPLIT_STR = ",";
    private static final Pattern PATTERN = Pattern.compile("([A-Za-z\\d]+)(_)?");
    private static final Pattern PATTERN2 = Pattern.compile("([A-Z][a-z\\d]*)(_)?");


    public static String list2String(List<?> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (Object string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    public static List<Long> string2ListLong(String str) {
        List<String> list = string2List(str);
        if (null == list) {
            return null;
        }
        return Lists.transform(list, new Function<String, Long>() {
            @Override
            public Long apply(String s) {
                return Long.valueOf(s);
            }
        });
    }

    public static List<String> string2List(String str) {
        if (null == str) {
            return null;
        }
        String[] strings = str.split(SPLIT_STR);
        return Arrays.asList(strings);
    }


    /**
     * 下划线转驼峰法
     *
     * @param line       源字符串
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return 转换后的字符串
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Matcher matcher = PATTERN.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line) {
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Matcher matcher = PATTERN2.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }

    public static boolean isNumeric(String str) {
        if (null==str||str.length()==0){
            return false;
        }
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57) {
                return false;
            }
        }
        return true;
    }

    public static String getShardingSubffix(long value){
        return String.format("_%02d", value);
    }

    public static String uuid() {
        return UUID.randomUUID().toString().toLowerCase();
    }

}
