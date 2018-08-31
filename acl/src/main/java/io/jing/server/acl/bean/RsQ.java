package io.jing.server.acl.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter@Setter@ToString
public class RsQ {
    @Size(min = 1,max = 200)
    private String name;
    @Min(1)
    private Integer page = 1;
    @Min(1)@Max(1000)
    private Integer size = 10;
}
