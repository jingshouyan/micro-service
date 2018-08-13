package io.jing.server.fitness.constant;

import io.jing.base.util.code.Code;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/8/13 18:09
 */
@Component
public class FitnessCode {
    public static final int CUSTOM_NOT_FUND = 60001;
    public static final int OPENID_IN_USE = 60002;
    public static final int CONTACT_WRONG = 60003;
    static {
        Code.regCode(CUSTOM_NOT_FUND,"custom not fund");
        Code.regCode(OPENID_IN_USE,"openId in use");
        Code.regCode(CONTACT_WRONG,"contact wrong");
    }
}
