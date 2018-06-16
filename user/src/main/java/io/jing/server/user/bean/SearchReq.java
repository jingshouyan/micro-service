package io.jing.server.user.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author jingshouyan
 * #date 2018/6/16 16:24
 */
@Getter@Setter@ToString
public class SearchReq {
    @NotNull@Size(min = 1,max = 200)
    private String q;
    @Min(1)
    private Integer page = 1;
    @Min(1)@Max(1000)
    private Integer size = 10;
}
