package io.jing.server.fitness.constant;

import io.jing.base.util.code.Code;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/8/13 18:09
 */
@Component
public class FitnessCode {
    private static final int CUSTOM_NOT_FUND = 60001;

    static {
        Code.regCode(CUSTOM_NOT_FUND,"custom not fund");
    }
}
