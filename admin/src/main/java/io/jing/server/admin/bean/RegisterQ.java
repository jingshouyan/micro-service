package io.jing.server.admin.bean;

import io.jing.server.admin.constant.AdminConstant;
import io.jing.server.admin.constant.AdminCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author jingshouyan
 * #date 2018/6/11 13:36
 */
@Getter
@Setter
@ToString
public class RegisterQ {
    @NotNull@Size(min = 6,max = 20)
    private String username;
    @NotNull(message = AdminConstant.INVALID_CODE_PREFIX+AdminCode.PASSWORD_ILLEGALLY)
    private String password;
    @NotNull@Size(min = 2,max = 20)
    private String nickname;
    private String icon = "";
    private Integer userType = 1;
}
