package io.jing.server.relationship.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author jingshouyan
 * #date 2018/6/12 17:23
 */
@Getter@Setter@ToString(callSuper = true)
public class ContactBean extends BaseBean {
    @Key
    @Column(length = Constant.ID_FIELD_LENGTH)
    private String id;
    @Column(comment = "我的id",index = true)
    private String myId;
    @Column(comment = "联系人id")
    private String userId;
    @Column(comment = "联系人昵称")
    private String nickname;
    @Column(comment = "联系人头像")
    private String icon;
    @Column(comment = "联系人备注")
    private String remark;
    @Column(comment = "联系人类型")
    private Integer type;
    @Column(comment = "版本号")
    private Long revision;

    public String genId(){
        assert myId != null;
        assert userId != null;
        id = myId+"#"+userId;
        return id;
    }
}
