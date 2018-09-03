package io.jing.server.crud.bean;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class D {
    @NonNull
    private String bean;
    @NonNull
    private String type;

    private Object id;

    private List<Object> ids;
}
