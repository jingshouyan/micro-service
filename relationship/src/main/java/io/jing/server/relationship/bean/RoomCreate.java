package io.jing.server.relationship.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/14 15:31
 */
@Getter@Setter@ToString
public class RoomCreate {

    @NotNull@Size(min = 2,max = 20)
    private String name;
    private String icon = "";
    @NotNull@Size(min = 1,max = 100)
    private List<RoomUser> roomUsers;

}
