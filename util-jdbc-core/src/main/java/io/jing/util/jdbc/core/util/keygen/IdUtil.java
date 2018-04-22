package io.jing.util.jdbc.core.util.keygen;

/**
 * IdUtil id 生成器
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
public class IdUtil {

    private static DefaultKeyGenerator keyGenerator = DefaultKeyGenerator.getInstance();

    public static long longId() {
        return keyGenerator.generateKey().longValue();
    }

    public static int intId() {
        return keyGenerator.generateKey().intValue();
    }

    public static String stringId() {
        return String.valueOf(longId());
    }

    public static String stringId(String preffix) {
        return preffix + stringId();
    }

}
