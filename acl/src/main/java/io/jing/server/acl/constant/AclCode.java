package io.jing.server.acl.constant;

import io.jing.base.util.code.Code;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/8/30 23:34
 */
@Component
public class AclCode {

    public static final int NOT_FOUND_RESOURCE = 404;
    public static final int PERMISSION_DENIED = 405;
    static {
        Code.regCode(NOT_FOUND_RESOURCE,"not fund");
        Code.regCode(PERMISSION_DENIED,"permission denied");
    }
}
