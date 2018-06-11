package io.jing.server.user.constant;

import io.jing.base.util.code.Code;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/6/11 17:43
 */
@Component
public class UserCode {
    public static final int USERNAME_IN_USE = 10001;

    static{
        Code.regCode(USERNAME_IN_USE,"username in use.");
    }
}
