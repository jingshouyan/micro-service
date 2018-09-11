package io.jing.server.admin.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author jingshouyan
 * #date 2018/6/11 19:56
 */
@Getter
@Setter
@ToString
public class LoginQ {
    @NotNull
    private String username;
    @NotNull
    private String password;

    private Integer clientType = 1;
}
