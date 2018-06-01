package io.jing.server.uuid;

import java.util.UUID;

/**
 * @author jingshouyan
 * #date 2018/5/30 19:32
 */
public class UUIDTest {

    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        UUID uuid1 = UUID.fromString(uuid.toString());
        System.out.println(uuid1);
        System.out.println(uuid.equals(uuid1));
    }
}
