package io.jing.base.util.id;

import java.util.UUID;

/**
 * @author jingshouyan
 * @date 2018/4/15 17:02
 */
public class IdGen {

    public static String gen(){
        return UUID.randomUUID().toString();
    }
}
