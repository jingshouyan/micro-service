package io.jing.server.user.bean;

import io.jing.server.user.constant.UserCode;
import io.jing.server.user.constant.UserConstant;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author jingshouyan
 * #date 2018/8/9 21:30
 */
@Getter@Setter
public class PwdReset {
    @NotNull
    private String userId;
    @NotNull(message = UserConstant.INVALID_CODE_PREFIX+UserCode.PASSWORD_ILLEGALLY)
    private String password;
}
