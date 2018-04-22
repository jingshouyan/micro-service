package io.jing.util.jdbc.core.util.db.sql.generator;

import lombok.Getter;

/**
 * sql 生成器工厂类
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
public class SqlGeneratorFactory {
    public static final String MYSQL = "mysql";
    public static final String ORACLE = "oracle";
    public static final String MSSQL = "mssql";
    public static final String KINGBASE = "kingbase";

    public static final String DATABASE_ID = "DATABASE_ID";

    @Getter
    private static String databaseId = MYSQL;

    static {
        String envDatabaseId = System.getenv(DATABASE_ID);
        if(envDatabaseId!=null){
            databaseId = envDatabaseId;
        }
//        databaseId = KINGBASE;
    }

    public static <T> SqlGenerator<T> getSqlGenerator(Class<T> clazz) {
        switch (databaseId.toLowerCase()) {
            case MYSQL:
                return new SqlGenerator4Mysql<>(clazz);
            case ORACLE:
                return new SqlGenerator4Oracle<>(clazz);
            case KINGBASE:
                return new SqlGenerator4Kingbase<>(clazz);
            case MSSQL:

            default:
                throw new IllegalArgumentException("Unsupported  database:" + databaseId);
        }
    }
}
