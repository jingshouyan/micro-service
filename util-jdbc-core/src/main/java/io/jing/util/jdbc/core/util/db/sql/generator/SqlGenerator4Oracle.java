package io.jing.util.jdbc.core.util.db.sql.generator;

import com.google.common.base.Strings;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.*;
import lombok.NonNull;

import java.util.List;

/**
 * oracle
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
public class SqlGenerator4Oracle<T> extends AbstractSqlGenerator<T> implements SqlGenerator<T> {

    @Override
    protected char valueSeparate() {
        return ' ';
    }

    public SqlGenerator4Oracle(Class<T> clazz) {
       super(clazz);
    }

    @Override
    public SqlPrepared query(List<Compare> compares, Page<T> page) {
        SqlPrepared sqlPrepared = new SqlPrepared();
        String sql = "SELECT * FROM " + tableName();
        SqlPrepared whereSql = where(compares);
        sql += whereSql.getSql();
        sql += orderBy(page.getOrderBies());
        int offset = (page.getPage() - 1) * page.getPageSize();
        int end = offset + page.getPageSize();
        String sql2 = "";
        if(offset<=0){
            sql2 = " SELECT A.*, ROWNUM FROM ("+sql+") A WHERE ROWNUM <= "+end;
        }else{
            sql2 = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM ("+sql+") A WHERE ROWNUM <= "+end+" ) WHERE RN > "+offset;
        }
        sqlPrepared.setSql(sql2);
        sqlPrepared.setParams(whereSql.getParams());
        return sqlPrepared;
    }

    @Override
    public SqlPrepared createTableSql() {
        SqlPrepared sqlPrepared = new SqlPrepared();
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ");
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
        String sql = "DROP TABLE " + tableName() ;
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
                    str = "NUMBER(4)";
                    break;
                case "short":
                    str = "NUMBER(6)";
                    break;
                case "int":
                case "integer":
                    str = "NUMBER(11)";
                    break;
                case "long":
                    str = "NUMBER(20)";
                    break;
                case "boolean":
                    str = "NUMBER(4)";
                    break;
                case "float":
                case "double":
                    str = "NUMBER(20,11)";
                    break;
                default:
                    if (column.getColumnLength() <= Constant.VARCHAR_MAX_LENGTH) {
                        str = "VARCHAR2(" + column.getColumnLength() + ")";
                    } else {
                        str = "CLOB";
                    }
                    break;
            }
        }
        str = "" + column.getColumnName() + " " + str +" ";
        return str;
    }

}
