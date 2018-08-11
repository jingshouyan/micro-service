package io.jing.server.user.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/16 16:36
 */
@Getter@Setter@ToString
public class UserIds {
    @NotNull
    @Size(min = 1,max = 1000)
    List<String> ids;
}
