package io.jing.server.message.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/5/30 17:11
 */
@Getter@Setter
@ToString(callSuper = true)
public class WsConnBean extends BaseBean {
    @Key
    @Column(length = Constant.ID_FIELD_LENGTH)
    private String id;
    private String ticket;
    private String userId;
    private Integer clientType;
    private String serviceInstance;
}
