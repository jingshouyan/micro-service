package io.jing.server.message.ws;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.validation.constraints.Min;


/**
 * @author jingshouyan
 * #date 2018/5/31 17:48
 */
@Data
public class Message {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private long message;
    private String username;
}
