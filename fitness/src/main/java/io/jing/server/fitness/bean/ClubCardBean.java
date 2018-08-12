package io.jing.server.fitness.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jingshouyan
 * #date 2018/8/7 20:49
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ClubCardBean extends BaseBean {

    @Key
    private Long id;

    @Column(comment = "会员卡名称")
    private String name;
    @Column(comment = "会员卡类型")
    private String type;
    @Column(comment = "会员卡时长，单位：天")
    private Integer days;
    @Column(comment = "会员卡说明")
    private String note;

}
