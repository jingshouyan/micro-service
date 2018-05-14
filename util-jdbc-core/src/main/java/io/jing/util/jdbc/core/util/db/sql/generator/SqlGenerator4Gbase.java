package io.jing.util.jdbc.core.util.db.sql.generator;

import com.google.common.base.Strings;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.*;
import lombok.NonNull;

import java.util.List;

/**
 * @author jingshouyan
 * @date 2018/5/9 18:56
 */
public class SqlGenerator4Gbase<T> extends AbstractSqlGenerator<T> {

    public SqlGenerator4Gbase(Class<T> clazz){
        super(clazz);
    }

    @Override
    protected char valueSeparate() {
        return ' ';
    }


    @Override
    public SqlPrepared query(List<Compare> compares, Page<T> page) {
        int offset = (page.getPage() - 1) * page.getPageSize();
        SqlPrepared sqlPrepared = new SqlPrepared();
        String sql = "SELECT skip "+offset+" first "+page.getPageSize()+" * FROM "+tableName();
        SqlPrepared whereSql = where(compares);
        sql += whereSql.getSql();
        sql += orderBy(page.getOrderBies());
        sqlPrepared.setSql(sql);
        sqlPrepared.setParams(whereSql.getParams());
        return sqlPrepared;
    }

    @Override
    public SqlPrepared createTableSql() {
        SqlPrepared sqlPrepared = new SqlPrepared();
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ");
        sql.append(tableName());
        sql.append(" (");
        BeanTable beanTable = Bean4DbUtil.getBeanTable(clazz);
        for (BeanColumn column : beanTable.getColumns()) {
            sql.append(columnString(column));
            sql.append(" ,");
        }
        sql.deleteCharAt(sql.length() - 1);
        BeanColumn key = beanTable.getKey();
        if (null != key) {
            sql.append(", PRIMARY KEY (");
            sql.append(key.getColumnName());
            sql.append(")");
        }
        sql.append(")");
        sqlPrepared.setSql(sql.toString());
        return sqlPrepared;
    }

    @Override
    public SqlPrepared dropTableSql() {
        SqlPrepared sqlPrepared = new SqlPrepared();
        String sql = "DROP TABLE IF EXIST" + tableName() ;
        sqlPrepared.setSql(sql);
        return sqlPrepared;
    }


    protected String columnString(@NonNull BeanColumn column) {
        String str;
        if (!Strings.isNullOrEmpty(column.getColumnType())) {
            str = column.getColumnType() + "(" + column.getColumnLength() + ")";
        } else {
            Class clazz = column.getField().getType();
            switch (clazz.getSimpleName().toLowerCase()) {
                case "byte":
                    str = "NUMERIC(4,0)";
                    break;
                case "short":
                    str = "NUMERIC(6,0)";
                    break;
                case "int":
                case "integer":
                    str = "NUMERIC(11,0)";
                    break;
                case "long":
                    str = "NUMERIC(24,0)";
                    break;
                case "boolean":
                    str = "NUMERIC(4,0)";
                    break;
                case "float":
                case "double":
                    str = "NUMERIC(20,11)";
                    break;
                default:
                    if (column.getColumnLength() < Constant.VARCHAR_MAX_LENGTH) {
                        str = "LVARCHAR(" + column.getColumnLength() + ")";
                    } else {
                        str = "TEXT";
                    }
                    break;
            }
        }
        str = "" + column.getColumnName() + " " + str +" ";
        return str;
    }
}
