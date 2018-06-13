package io.jing.server.relationship.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author jingshouyan
 * #date 2018/6/13 11:09
 */
@Getter@Setter@ToString
public class ContactsAdd {
    @NotNull
    private String userId;
    private String remark = "";
    private Integer type = 1;
}
