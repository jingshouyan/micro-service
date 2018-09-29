package io.jing.server.relationship.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/6/13 20:59
 */
@Getter@Setter@ToString
public class RoomBean extends BaseBean {
    @Key
    private String id;
    @Column(comment = "房间名称")
    private String name;
    @Column(comment = "房间图标")
    private String icon;
    @Column(comment = "房间人数")
    private Integer userCount;
    @Column(comment = "版本号")
    private Long revision;
}
