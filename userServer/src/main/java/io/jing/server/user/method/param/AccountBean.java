package io.jing.server.user.method.param;

import io.jing.util.jdbc.core.util.db.annotation.Column;
import lombok.Data;

/**
 * @author jingshouyan
 * @date 2018/4/19 10:25
 */
@Data
public class AccountBean {
    @Column(comment="用户ID")
    private String userId;
    @Column(comment="账号类型 1.电话 2.邮箱 3.自定义")
    private Integer type;
    @Column(comment="账号名",encrypt = true)
    private String contactInfo;
    @Column(comment="账号名(小写)",encrypt = true)
    private String contactMark;
    @Column(comment="账号状态 1.正常 2.停用 3.限制登录")
    private Integer status;
}
