package io.jing.server.fitness.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Ignore;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import io.jing.util.jdbc.core.util.db.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/8/7 21:19
 */
@Getter
@Setter
@ToString(callSuper = true)
@Table(comment = "客户信息")
public class CustomBean extends BaseBean {
    @Key
    private Long id;
    @Column(comment = "微信 openId")
    private String wxOpenId;
    @Column(comment = "客户姓名")
    private String name;
    @Column(comment = "联系方式")
    private String contact;
    @Column(comment = "身高")
    private Double height;
    @Column(comment = "体重")
    private Double weight;
    @Column(comment = "出生年 yyyy")
    private String birthyear;
    @Column(comment = "生日 MMdd")
    private String birthday;
    @Column(comment = "会员卡id")
    private Long cardId;
    @Column(comment = "会员卡有效期 yyyyMMdd")
    private String cardPeriod;

    @Ignore
    private ClubCardBean clubCard;

}
