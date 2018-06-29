package io.jing.server.sms.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/6/28 18:45
 */
@Getter@Setter@ToString(callSuper = true)
public class SmsRecord extends BaseBean {
    @Key
    @Column(comment = "id")
    private String id;
    @Column(comment = "手机号")
    private String mobile;
    @Column(comment = "验证码")
    private String code;
    @Column(comment = "验证码类型")
    private Integer codeType;
    @Column(comment = "验证码状态 1 正常 2 失效")
    private Integer codeState;
    @Column(comment = "接口返回message，通常为messageId")
    private String msg;
    @Column(comment = "接口返回code")
    private String resultCode;
    @Column(comment = "验证次数")
    private Integer verifyNum;
    @Column(comment = "验证时间")
    private Long verifyAt;
    @Column(comment = "失效时间")
    private Long expireAt;

}
