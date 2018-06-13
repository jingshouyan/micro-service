package io.jing.server.relationship.bean;

import io.jing.base.constant.BaseConstant;
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
public class ContactsBean extends BaseBean {
    @Key
    @Column(length = Constant.ID_FIELD_LENGTH)
    private String id;
    private String myId;
    private String userId;
    private String remark;
    private Integer type;
    private Long revision;

    public String genId(){
        assert myId != null;
        assert userId != null;
        id = myId+"#"+userId;
        return id;
    }
}
