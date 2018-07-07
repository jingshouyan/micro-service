package io.jing.server.sms.bean;

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
public class UserReg {
    @NotNull@Size(min = 6,max = 20)
    private String email;
    @NotNull
    private String password;
    @NotNull@Size(min = 2,max = 20)
    private String fullName;
    private String icon = "";
    private Integer userType = 1;
}
