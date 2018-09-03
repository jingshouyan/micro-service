package io.jing.server.query.bean;


import com.google.common.collect.Lists;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.Page;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter@Setter@ToString
public class Q {
    @NonNull
    private String bean;
    @NonNull
    private List<Compare> compares = Lists.newArrayList();
    @NonNull
    private Page page = new Page();
    @NonNull
    private String type;

    private Object id;

    private List<Object> ids;

}
