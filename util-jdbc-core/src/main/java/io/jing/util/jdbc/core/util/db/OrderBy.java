package io.jing.util.jdbc.core.util.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * OrderBy
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
@ToString
@AllArgsConstructor()
public class OrderBy {
    @Getter
    private String key;
    @Getter
    private boolean asc = true;


    public static OrderBy newInstance(String key) {
        return newInstance(key, true);
    }

    public static OrderBy newInstance(String key, boolean asc) {
        return new OrderBy(key, asc);
    }
}
