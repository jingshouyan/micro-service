package io.jing.server.message.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
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
    private String ticket;
    private String userId;
    private Integer clientType;
    private String serviceInstance;
}
