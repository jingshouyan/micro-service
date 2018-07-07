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
    public static final int PASSWORD_ILLEGALLY = 10002;
    public static final int USER_UNREGISTER = 10003;
    public static final int PASSWORD_WRONG = 10004;
    public static final int TICKET_INVALID = 10005;
    public static final int UNSER_NOT_FOUND = 10006;
    static{
        Code.regCode(USERNAME_IN_USE,"username in use");
        Code.regCode(PASSWORD_ILLEGALLY,"password illegally");
        Code.regCode(USER_UNREGISTER,"user unregister");
        Code.regCode(PASSWORD_WRONG,"password wrong");
        Code.regCode(TICKET_INVALID,"ticket invalid");
        Code.regCode(UNSER_NOT_FOUND,"user not found");
    }
}
