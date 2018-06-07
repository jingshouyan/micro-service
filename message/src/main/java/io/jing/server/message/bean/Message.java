package io.jing.server.message.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/1 11:18
 */
@Data
public class Message {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long id;
    private String senderId;
    @NotNull
    private String targetId;
    @NotNull
    private String targetType;
    @NotNull
    private String messageType;
    private Text text;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long flag;
    private List<String> relatedUsers;
    private long sentAt;

    public boolean selfMessage(){
        return senderId !=null && senderId.equals(targetId);
    }

    private static final int LONG_MAX_POSITION = 63;
    public boolean yon(int position){
        if(position>LONG_MAX_POSITION||position<0){
            return false;
        }
        return (flag>>position & 1L) == 1L;
    }

    public Message bitSet(int position,boolean yon){
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
