package io.jing.server.admin.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AdminBean extends BaseBean {
    @Key
    private String id;
    @Column(index = true,comment = "账号")
    private String username;
    @Column(comment = "昵称")
    private String nickname;
    @Column(length = Constant.VARCHAR_MAX_LENGTH,comment = "头像")
    private String icon;
    @JsonIgnore
    @Column(comment = "密码hash")
    private String pwHash;
    @Column(comment = "用户类型")
    private Integer userType;
}
