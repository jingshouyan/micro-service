package io.jing.server.relationship.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/6/13 20:59
 */
@Getter@Setter@ToString
public class RoomBean extends BaseBean {
    private String id;
    private String name;
    private String icon;
}
