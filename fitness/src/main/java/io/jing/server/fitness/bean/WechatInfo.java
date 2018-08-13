package io.jing.server.fitness.bean;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author jingshouyan
 * #date 2018/8/13 18:16
 */
@Getter@Setter
public class WechatInfo {

    @NotNull
    private String openId;

}
