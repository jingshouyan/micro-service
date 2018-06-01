package io.jing.server.message.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jingshouyan
 * #date 2018/6/1 11:28
 */
@Data
public class Text {
    @NotNull
    private String content;
}
