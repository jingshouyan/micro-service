package io.jing.server.relationship.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Ignore;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/6/14 14:08
 */
@Getter@Setter@ToString(callSuper = true)
public class RoomUserBean extends BaseBean {
    @Key
    private String id;
    @Column(comment = "房间id",index = true)
    private String roomId;
    @Column(comment = "成员id",index = true)
    private String userId;
    @Column(comment = "成员昵称")
    private String nickname;
    @Column(comment = "成员头像")
    private String icon;
    @Column(comment = "成员备注")
    private String remark;
    @Column(comment = "成员级别")
    private Integer userLevel;
    @Column(comment = "成员版本号")
    private Long revisionUser;
    @Column(comment = "房间版本号")
    private Long revisionRoom;
    @Ignore
    private RoomBean room;

    public String genId(){
        assert roomId != null;
        assert userId != null;
        return roomId+"#"+userId;
    }

}
