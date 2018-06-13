package io.jing.server.user.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/6/11 10:47
 */
@Getter
@Setter
@ToString(callSuper = true)
public class TokenBean extends BaseBean {
    @Key
    private String ticket;
    private String userId;
    private Integer userType;
    private Integer clientType;
}
