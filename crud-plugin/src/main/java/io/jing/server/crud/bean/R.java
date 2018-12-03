package io.jing.server.crud.bean;


import com.google.common.collect.Lists;
import io.jing.server.crud.constant.CrudConstant;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.Page;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter@Setter@ToString
public class R {
    @NotNull
    private String bean;
    @NotNull
    private List<Compare> compares = Lists.newArrayList();
    @NotNull
    private Page page = new Page();
    @NotNull
    private String type = CrudConstant.TYPE_SINGLE;

    private Object id;

    private List<Object> ids;

}
