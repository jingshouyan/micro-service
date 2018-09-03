package io.jing.server.crud.bean;

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
    private String type;
    @NonNull
    private String data;

}
