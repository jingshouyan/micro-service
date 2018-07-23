package io.jing.server.sms.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/7/16 19:10
 */
@Getter
@Setter
@ToString(callSuper = true)
public class PayOrder extends BaseBean {

    @Key
    private String orderNumber;
    private Integer payId;
    private String payChannel;
    private String subject;
    private Integer money;
    private String attachData;
    private Integer state;

    private String userId;
    private String clientOrder;
}
