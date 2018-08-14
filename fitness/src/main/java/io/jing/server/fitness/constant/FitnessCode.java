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
    public static final int CARD_OVERDUE = 60004;
    public static final int CARD_EXHAUSTED = 60005;
    public static final int LESSON_NOT_FUND = 60006;
    public static final int BOOKING_IS_FULL = 60007;
    static {
        Code.regCode(CUSTOM_NOT_FUND,"custom not fund");
        Code.regCode(OPENID_IN_USE,"openId in use");
        Code.regCode(CONTACT_WRONG,"contact wrong");
        Code.regCode(CARD_OVERDUE,"card overdue");
        Code.regCode(CARD_EXHAUSTED,"card exhausted");
        Code.regCode(LESSON_NOT_FUND,"lesson not fund");
        Code.regCode(BOOKING_IS_FULL,"booking is full");
    }
}
