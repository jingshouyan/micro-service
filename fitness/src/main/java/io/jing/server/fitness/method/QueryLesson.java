package io.jing.server.fitness.method;

import io.jing.server.fitness.bean.LessonBean;
import io.jing.server.fitness.bean.LessonQ;
import io.jing.server.fitness.dao.LessonDao;
import io.jing.server.method.Method;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.db.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("fitness.queryLesson")
public class QueryLesson implements Method<LessonQ> {

    private LessonDao lessonDao;

    @Override
    public Object action(LessonQ lessonQ) {
        CompareUtil util = CompareUtil.newInstance();
        if(!StringUtils.isBlank(lessonQ.getDate())){
            util.field("startAt").like(lessonQ.getDate()+"%");
        }
        if(!StringUtils.isBlank(lessonQ.getName())){
            util.field("name").like("%"+lessonQ.getName()+"%");
        }
        if(!StringUtils.isBlank(lessonQ.getLocation())){
            util.field("location").like("%"+lessonQ.getLocation()+"%");
        }
        if(!StringUtils.isBlank(lessonQ.getType())){
            util.field("type").like("%"+lessonQ.getType()+"%");
        }
        List<Compare> compares = util.compares();
        Page<LessonBean> page = new Page<>();
        page.setPage(lessonQ.getPage());
        page.setPageSize(lessonQ.getPageSize());
        page.addOrderBy("startAt");
        return lessonDao.query(compares,page);
    }
}
