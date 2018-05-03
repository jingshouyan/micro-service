package io.jing.util.jdbc.core.util.db;

import com.fasterxml.jackson.databind.JavaType;
import io.jing.util.jdbc.core.util.Constant;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author jingshouyan
 * @date 2018/4/24 17:25
 */
@Slf4j
public class BeanRowMapper2<T> implements RowMapper<T> ,Constant {
    private BeanTable beanTable;
    private ConversionService conversionService = DefaultConversionService.getSharedInstance();
    @Getter
    private Class<T> mappedClass;
    public BeanRowMapper2(Class<T> mappedClass){
        init(mappedClass);
    }

    private void init(Class<T> mappedClass){
        this.mappedClass = mappedClass;
        this.beanTable = Bean4DbUtil.getBeanTable(mappedClass);
    }


    @Override
    public T mapRow(ResultSet rs, int i) throws SQLException{
        assert this.mappedClass != null :"Mapped class was not specified";
        T mappedObject = BeanUtils.instantiateClass(this.mappedClass);
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
        initBeanWrapper(bw);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int j = 1; j <= columnCount; j++) {
            String column = JdbcUtils.lookupColumnName(rsmd, j).toLowerCase();
            BeanColumn beanColumn = beanTable.getLowerCaseColumnMap().get(column);
            Class<?> clazz = String.class;
            if(!beanColumn.isJson()){
                clazz = beanColumn.getField().getType();
            }
            Object value = JdbcUtils.getResultSetValue(rs, j, clazz);
            if(value == null){
                continue;
            }
            if(beanColumn.isJson()){
                try {
                    if (beanColumn.isList()) {
                        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructArrayType(beanColumn.getJsonType());
                        value = OBJECT_MAPPER.readValue(value.toString(), javaType);
                    } else {
                        value = OBJECT_MAPPER.readValue(value.toString(), beanColumn.getJsonType());
                    }
                }catch (Exception e){
                    throw new SQLException(e);
                }
            }
            bw.setPropertyValue(beanColumn.getFieldName(), value);
        }
        return mappedObject;
    }

    private void initBeanWrapper(BeanWrapper beanWrapper){
        beanWrapper.setConversionService(this.conversionService);
    }

}
