package io.jing.server.crud.bean;


import com.google.common.collect.Lists;
import io.jing.server.crud.constant.CrudConstant;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.Page;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter@Setter@ToString
public class R {
    @NonNull
    private String bean;
    @NonNull
    private List<Compare> compares = Lists.newArrayList();
    @NonNull
    private Page page = new Page();
    @NonNull
    private String type = CrudConstant.TYPE_SINGLE;

    private Object id;

    private List<Object> ids;

}
