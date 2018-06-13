package io.jing.server.relationship.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

/**
 * @author jingshouyan
 * #date 2018/6/12 20:19
 */
@Getter@Setter@ToString
public class QueryParam {
    private long revision = 0;
    @Min(10)
    private int size = 10;
    private boolean containDel = true;
}
