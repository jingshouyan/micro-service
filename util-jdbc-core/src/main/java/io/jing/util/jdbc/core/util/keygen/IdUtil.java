package io.jing.util.jdbc.core.util.keygen;

import lombok.Getter;
import lombok.Setter;

/**
 * IdUtil id 生成器
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
public class IdUtil {

    @Getter
    @Setter
    private static KeyGenerator keyGenerator = DefaultKeyGenerator.getInstance();


    public static long longId(String type) {
        return keyGenerator.genId(type);
    }

    public static int intId(String type) {
        return (int) longId(type);
    }

    public static String stringId(String type) {
        return String.valueOf(longId(type));
    }


}
