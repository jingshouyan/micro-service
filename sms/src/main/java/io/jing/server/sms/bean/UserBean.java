package io.jing.server.sms.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/7/4 15:08
 */
@Getter
@Setter
@ToString(callSuper = true,exclude = "id")
public class UserBean extends BaseBean {
    @Key
    @Column(comment = "id")
    private String id;
    @Column(comment = "账号")
    private String username;
    @JsonIgnore
    @Column(comment = "密码")
    private String pwHash;
    @Column(comment="昵称")
    private String nickname;

    @Column(comment = "头像",length = Constant.VARCHAR_MAX_LENGTH)
    private String icon;
    @Column(comment = "用户类型")
    private Integer userType;
}
