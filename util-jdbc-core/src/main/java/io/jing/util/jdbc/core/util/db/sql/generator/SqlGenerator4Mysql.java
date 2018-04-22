package io.jing.util.jdbc.core.util.db.sql.generator;

import com.google.common.base.Strings;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.*;
import lombok.NonNull;

import java.util.List;

/**
 * mysql
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
public class SqlGenerator4Mysql<T> extends AbstractSqlGenerator<T> implements SqlGenerator<T> {


    public SqlGenerator4Mysql(Class<T> clazz) {
        super(clazz);
    }

    @Override
    protected String q(){
        return "`";
    }

    @Override
    public SqlPrepared query(List<Compare> compares, Page<T> page) {
        SqlPrepared sqlPrepared = new SqlPrepared();
        String sql = "SELECT * FROM " + tableName();
        SqlPrepared whereSql = where(compares);
        sql += whereSql.getSql();
        sql += orderBy(page.getOrderBies());
        int offset = (page.getPage() - 1) * page.getPageSize();
        sql += " LIMIT " + offset + "," + page.getPageSize();
        sqlPrepared.setSql(sql);
        sqlPrepared.setParams(whereSql.getParams());
        return sqlPrepared;
    }

    @Override
    public SqlPrepared createTableSql() {
        SqlPrepared sqlPrepared = new SqlPrepared();
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS `");
        sql.append(tableName());
        sql.append("` (");
        BeanTable beanTable = Bean4DbUtil.getBeanTable(clazz);
        for (BeanColumn column : beanTable.getColumns()) {
            sql.append(columnString(column));
            sql.append(" ,");
        }
        sql.deleteCharAt(sql.length() - 1);
        BeanColumn key = beanTable.getKey();
        if (null != key) {
            sql.append(", PRIMARY KEY (`");
            sql.append(key.getColumnName());
            sql.append("`)");
        }
        for (BeanColumn column : beanTable.getColumns()) {
            if (column.isIndex()) {
                sql.append(", KEY (`");
                sql.append(column.getColumnName());
                sql.append("`)");
            }
        }
        sql.append(")  COMMENT='"+ tableComment()+"';");
        sqlPrepared.setSql(sql.toString());
        return sqlPrepared;
    }

    @Override
    public SqlPrepared dropTableSql() {
        SqlPrepared sqlPrepared = new SqlPrepared();
        String sql = "DROP TABLE IF EXISTS `" + tableName() + "`;";
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
                    str = "tinyint";
                    break;
                case "short":
                    str = "smallint";
                    break;
                case "int":
                case "integer":
                    str = "int";
                    break;
                case "long":
                    str = "bigint";
                    break;
                case "boolean":
                    str = "tinyint";
                    break;
                case "float":
                case "double":
                    str = "double";
                    break;
                default:
                    if (column.getColumnLength() < Constant.VARCHAR_MAX_LENGTH) {
                        str = "varchar(" + column.getColumnLength() + ")";
                    } else {
                        str = "text";
                    }
                    break;
            }
        }
        str = "`" + column.getColumnName() + "` " + str +" COMMENT '"+column.getComment()+"'";
        return str;
    }

}
