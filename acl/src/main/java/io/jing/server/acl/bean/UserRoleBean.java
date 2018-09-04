package io.jing.server.acl.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import io.jing.util.jdbc.core.util.db.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@Table(comment = "用户角色")
public class UserRoleBean extends BaseBean {
    @Key
    @Column(comment = "用户Id")
    private String userId;
    @Column(comment = "角色Id 列表")
    private List<Long> roleIds;
}
