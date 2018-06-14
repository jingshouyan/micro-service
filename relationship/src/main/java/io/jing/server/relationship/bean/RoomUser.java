package io.jing.server.relationship.bean;

import io.jing.server.relationship.constant.RelationshipConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author jingshouyan
 * #date 2018/6/14 15:48
 */
@Getter@Setter@ToString
public class RoomUser {
    @NotNull
    private String userId;
    private String remark = "";
    private int userLevel = RelationshipConstant.ROOM_LEVEL_NORMAL;
}
