package io.jing.server.crud.bean;

import io.jing.server.crud.constant.CrudConstant;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class U {
    @NonNull
    private String bean;
    @NonNull
    private String type = CrudConstant.TYPE_SINGLE;
    @NonNull
    private String data;

}
