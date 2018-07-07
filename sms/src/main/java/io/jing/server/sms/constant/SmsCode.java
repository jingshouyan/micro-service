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
    public static final int ACCOUNT_NOT_EXIST = 30003;
    public static final int PASSWORD_WRONG = 30004;
    public static final int JWT_ERROR = 30005;

    static {
        Code.regCode(CODE_EXPIRE,"code expire");
        Code.regCode(CODE_INVALID,"code invalid");
        Code.regCode(ACCOUNT_NOT_EXIST,"account not exist");
        Code.regCode(PASSWORD_WRONG,"wrong password");
        Code.regCode(JWT_ERROR,"jwt error");
    }
}
