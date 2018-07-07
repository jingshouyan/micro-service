package io.jing.server.gateway.constant;

import io.jing.base.util.code.Code;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/7/6 23:20
 */
@Component
public class AppCode {
    public static final int TOKEN_INVALID = 990001;

    static {
        Code.regCode(TOKEN_INVALID,"token invalid");
    }
}
