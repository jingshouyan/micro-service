package io.jing.server.message.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/4 14:05
 */
@Data
public class MessagePush {
    @NotNull
    Message message;
    @NotNull@Size(min = 1)
    List<String> connIdList;
}
