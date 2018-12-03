package io.jing.server.crud.bean;

import io.jing.server.crud.constant.CrudConstant;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class U {
    @NotNull
    private String bean;
    @NotNull
    private String type = CrudConstant.TYPE_SINGLE;
    @NotNull
    private String data;

}
