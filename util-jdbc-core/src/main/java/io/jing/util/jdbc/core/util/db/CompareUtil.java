package io.jing.util.jdbc.core.util.db;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 用于生成 List<Compare> 的工具类
 * List<Compare> compares = CompareUtil.newInstance().field("id").eq(123).field("age").gte(20).lt(60).compares();
 *
 * @author jingshouyan 290173092@qq.com
 * @date 2018/3/30 10:28
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompareUtil {

    public static CompareUtil newInstance(){
        return new CompareUtil();
    }

    private List<Compare> compares = Lists.newArrayList();

    public List<Compare> compares(){
        return compares;
    }

    private Compare last;

    public CompareUtil field(String field){
        last = new Compare();
        compares.add(last);
        last.setField(field);
        return this;
    }
    public CompareUtil like(String like){
        last.setLike(like);
        return this;
    }
    public CompareUtil gt(Object gt){
        last.setGt(gt);
        return this;
    }
    public CompareUtil lt(Object lt){
        last.setLt(lt);
        return this;
    }
    public CompareUtil gte(Object gte){
        last.setGte(gte);
        return this;
    }
    public CompareUtil lte(Object lte){
        last.setLte(lte);
        return this;
    }
    public CompareUtil eq(Object eq){
        last.setEq(eq);
        return this;
    }
    public CompareUtil ne(Object ne){
        last.setNe(ne);
        return this;
    }
    public CompareUtil in(List<?> in){
        last.setIn(in);
        return this;
    }
    public CompareUtil notIn(List<?> notIn){
        last.setNotIn(notIn);
        return this;
    }
    public CompareUtil empty(boolean empty){
        last.setEmpty(empty);
        return this;
    }

    public static List<Compare> compares(Map<String,Object> condition){
        List<Compare> compares = Lists.newArrayList();
        if(null != condition && !condition.isEmpty()){
            condition.forEach((key,value)->{
                if(value instanceof Compare){
                    Compare c = (Compare) value;
                    c.setField(key);
                    compares.add(c);
                } else {
                    Compare c = new Compare();
                    c.setField(key);
                    c.setEq(value);
                    compares.add(c);
                }
            });
        }
        return compares;
    }
}
