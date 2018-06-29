package io.jing.server.sms.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jingshouyan
 * #date 2018/6/28 19:29
 */
@Data
public class CodeVerify {
    @NotNull
    private String mobile;
    private int codeType;
    @NotNull
    private String code;
}
