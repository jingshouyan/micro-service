package io.jing.server.relationship.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/6/14 14:08
 */
@Getter@Setter@ToString(callSuper = true)
public class RoomUserBean extends BaseBean {
    private String id;
    private String roomId;
    private String userId;
    private String nickname;
    private String icon;
    private String remark;
    private Integer userLevel;
    private Long revisionUser;
    private Long revisionRoom;

}
