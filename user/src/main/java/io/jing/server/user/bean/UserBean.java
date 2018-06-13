package io.jing.server.user.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * @date 2018/4/19 10:22
 */
@Getter
@Setter
@ToString(callSuper = true)
public class UserBean extends BaseBean {
    @Key
    private String id;
    private String username;
    private String pwHash;
    private Integer userType;
}
