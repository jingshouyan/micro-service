package io.jing.server.user.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author jingshouyan
 * #date 2018/8/9 22:15
 */
@Getter@Setter
public class UserEdit {
    private String userId = "";
    private String nickname;
    private String icon;
}
