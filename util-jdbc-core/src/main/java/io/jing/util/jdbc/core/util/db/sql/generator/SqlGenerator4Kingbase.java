package io.jing.util.jdbc.core.util.db.sql.generator;


import com.google.common.base.Strings;
import io.jing.util.jdbc.core.util.Constant;
import io.jing.util.jdbc.core.util.db.BeanColumn;
import lombok.NonNull;
/**
 * Kingbase
 * @author jingshouyan
 * @date 2018/4/14 17:25
 */
public class SqlGenerator4Kingbase<T> extends SqlGenerator4Oracle<T> {
    public SqlGenerator4Kingbase(Class<T> clazz) {
        super(clazz);
    }

    @Override
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
                    if (column.getColumnLength() <= Constant.VARCHAR_MAX_LENGTH) {
                        str = "VARCHAR(" + column.getColumnLength() + ")";
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
