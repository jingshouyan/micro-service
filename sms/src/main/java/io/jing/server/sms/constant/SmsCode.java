package io.jing.server.sms.constant;

import io.jing.base.util.code.Code;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/6/28 19:32
 */
@Component
public class SmsCode {
    public static final int CODE_EXPIRE = 30001;
    public static final int CODE_INVALID = 30002;

    static {
        Code.regCode(CODE_EXPIRE,"code expire");
        Code.regCode(CODE_INVALID,"code invalid");
    }
}
