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
}
