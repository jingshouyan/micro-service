package io.jing.server.sms.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jingshouyan
 * #date 2018/6/28 19:11
 */
@Data
public class CodeSend {
    @NotNull
    private String mobile;
    private int codeType;
}
