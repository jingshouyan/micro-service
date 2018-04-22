package io.jing.util.jdbc.core.util.db;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Page
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
@Data
public class Page<T> {
    private int page = 1;
    private int pageSize = 10;
    private int totalPage;
    private int totalCount;
    private List<T> list;

    private List<OrderBy> orderBies = Lists.newArrayList();


    public void totalCount(int totalCount) {
        int tp = totalCount / pageSize;
        int y = totalCount % pageSize;
        if (0 != y) {
            tp++;
        }
        setTotalPage(tp);
        this.totalCount = totalCount;
    }

    public void addOrderBy(OrderBy orderBy) {
        orderBies.add(orderBy);
    }

    public void addOrderBy(String key) {
        addOrderBy(key, true);
    }

    public void addOrderBy(String key, boolean asc) {
        addOrderBy(OrderBy.newInstance(key, asc));
    }

}
