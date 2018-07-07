package io.jing.server.sms.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jingshouyan
 * #date 2018/7/4 15:18
 */
@Data
public class LoginBean {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
