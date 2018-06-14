package io.jing.server.user.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.annotation.Column;
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
    private String nickname;
    @Column(length = Constant.VARCHAR_MAX_LENGTH)
    private String icon;
    private String pwHash;
    private Integer userType;
}
