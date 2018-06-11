package io.jing.server.user.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author jingshouyan
 * #date 2018/6/11 13:36
 */
@Getter@Setter
public class UserReg {
    @NotNull@Size(min = 6,max = 20)
    private String username;
    @NotNull
    private String password;
    private Integer userType = 1;
}
