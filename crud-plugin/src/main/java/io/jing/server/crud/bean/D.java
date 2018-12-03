package io.jing.server.crud.bean;

import io.jing.server.crud.constant.CrudConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class D {
    @NotNull
    private String bean;
    @NotNull
    private String type = CrudConstant.TYPE_SINGLE;

    private Object id;

    private List<Object> ids;
}
