package io.jing.server.message.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import lombok.*;

/**
 * @author jingshouyan
 * #date 2018/5/30 16:21
 */
@Getter@Setter
@ToString(callSuper = true)
public class MessageBean extends BaseBean {
    private String userId;
    private String senderId;
    private String targetId;
    private String targetType;
    private String type;
    @Column(length = Constant.VARCHAR_MAX_LENGTH)
    private String data;
    private Long flag;
    private Integer read1;
    private Integer read2;
    private Integer read3;
    private Integer read;
}
