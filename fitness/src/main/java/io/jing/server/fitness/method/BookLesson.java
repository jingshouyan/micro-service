package io.jing.server.fitness.method;

import io.jing.server.fitness.bean.BookQ;
import io.jing.server.fitness.dao.BookDao;
import io.jing.server.fitness.dao.CustomDao;
import io.jing.server.fitness.dao.LessonDao;
import io.jing.server.method.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("fitness.bookLesson")
public class BookLesson implements Method<BookQ> {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private CustomDao customDao;
    @Autowired
    private LessonDao lessonDao;

    @Override
    public Object action(BookQ bookQ) {
        return null;
    }
}
