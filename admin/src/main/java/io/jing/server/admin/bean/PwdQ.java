package io.jing.server.admin.bean;

import io.jing.server.admin.constant.AdminConstant;
import io.jing.server.admin.constant.AdminCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author jingshouyan
 * #date 2018/8/9 21:30
 */
@Getter@Setter
public class PwdQ {
    @NotNull
    private String userId;
    @NotNull(message = AdminConstant.INVALID_CODE_PREFIX+AdminCode.PASSWORD_ILLEGALLY)
    private String password;
}
