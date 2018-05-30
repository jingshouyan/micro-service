package io.jing.ip.test.env;

/**
 * @author jingshouyan
 * @date 2018/5/8 11:19
 */
public class EnvTest {

    public static void main(String[] args) {
        String zk_host = System.getenv("ZOOKEEPER_URL");
        System.out.println(zk_host);
    }
}
