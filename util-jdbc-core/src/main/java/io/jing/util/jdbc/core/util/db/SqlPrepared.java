package io.jing.util.jdbc.core.util.db;

import lombok.Data;

import java.util.Map;

/**
 * SqlPrepared
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
@Data
public class SqlPrepared {
    private String sql;
    private Map<String, Object> params;
}
