package io.jing.server.relationship.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/14 17:48
 */
@Getter@Setter@ToString
public class RoomUserAdd {

    @NotNull
    private String roomId;

    @NotNull@Size(min = 1,max = 100)
    private List<RoomUser> roomUsers;
}
