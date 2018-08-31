package io.jing.server.acl.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Ignore;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import io.jing.util.jdbc.core.util.db.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/8/30 23:35
 */
@Getter
@Setter
@ToString(callSuper = true)
@Table(comment = "角色信息")
public class RoleBean extends BaseBean {

    @Key
    private Long id;
    @Column(comment = "角色名称")
    private String name;
    @Column(comment = "说明")
    private String description;
    @Column(comment = "角色资源 id 列表",json = true)
    private List<Long> resourceIds;
    @Column(comment = "状态 1 有效，2 失效")
    private Integer state;
    @Ignore
    private List<ResourceBean> resources;
}
