package io.jing.server.message.bean;

import io.jing.util.jdbc.core.bean.BaseBean;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.annotation.Column;
import io.jing.util.jdbc.core.util.db.annotation.Key;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/5/30 16:21
 */
@Getter@Setter
@ToString(callSuper = true)
public class MessageBean extends BaseBean {
    @Key
    @Column(length = Constant.ID_FIELD_LENGTH)
    private String id;
    private String userId;
    private Long messageId;
    private String senderId;
    private String targetId;
    private String targetType;
    private String messageType;
    @Column(length = Constant.VARCHAR_MAX_LENGTH)
    private String data;
    private Long flag;
    @Column(length = Constant.VARCHAR_MAX_LENGTH,json = true)
    private List<String> relatedUsers;
    private Long sentAt;
    private Integer push1;
    private Integer push2;
    private Integer push3;
    private Integer push;


    private static final int LONG_MAX_POSITION = 63;
    public boolean yon(int position){
        if(position>LONG_MAX_POSITION||position<0){
            return false;
        }
        return (flag>>position & 1L) == 1L;
    }

    public MessageBean bitSet(int position,boolean yon){
        if( position >= 0 && position <= LONG_MAX_POSITION ){
            if(yon){
                flag = 1L<<position | flag;
            }else {
                flag = ~(1L<<position) & flag;
            }
        }
        return this;
    }
}
