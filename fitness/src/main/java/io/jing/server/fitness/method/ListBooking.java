package io.jing.server.fitness.method;

import io.jing.server.fitness.bean.BookBean;
import io.jing.server.fitness.bean.LessonBean;
import io.jing.server.fitness.bean.ListBookingQ;
import io.jing.server.fitness.dao.BookDao;
import io.jing.server.fitness.dao.LessonDao;
import io.jing.server.method.Method;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.db.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("fitness.listBooking")
public class ListBooking implements Method<ListBookingQ> {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private LessonDao lessonDao;

    @Override
    public Object action(ListBookingQ listBookingQ) {
        Page<BookBean> page = new Page<>();
        page.setPage(listBookingQ.getPage());
        page.setPageSize(listBookingQ.getPageSize());
        page.addOrderBy("createdAt",false);
        List<Compare> compares = CompareUtil.newInstance()
                .field("customId").eq(listBookingQ.getCustomId())
                .compares();
        page = bookDao.query(compares,page);
        List<Long> lessonIds = page.getList().stream()
                .map(BookBean::getLessonId).collect(Collectors.toList());
        List<Compare> compares1 = CompareUtil.newInstance()
                .field("id").in(lessonIds)
                .compares();
        List<LessonBean> lessonBeans = lessonDao.query(compares1);
        Map<Long,LessonBean> map = lessonBeans.stream()
                .collect(Collectors.toMap(LessonBean::getId, l->l));
        page.getList().forEach(bookBean -> bookBean.setLesson(map.get(bookBean.getLessonId())));

        return page;
    }
}
