package io.jing.server.fitness.method;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.db.helper.IdHelper;
import io.jing.server.fitness.bean.BookBean;
import io.jing.server.fitness.bean.BookQ;
import io.jing.server.fitness.bean.CustomBean;
import io.jing.server.fitness.bean.LessonBean;
import io.jing.server.fitness.constant.FitnessCode;
import io.jing.server.fitness.constant.FitnessConstant;
import io.jing.server.fitness.dao.BookDao;
import io.jing.server.fitness.dao.CustomDao;
import io.jing.server.fitness.dao.LessonDao;
import io.jing.server.method.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component("fitness.bookLesson")
public class BookLesson implements Method<BookQ> {

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private BookDao bookDao;
    @Autowired
    private CustomDao customDao;
    @Autowired
    private LessonDao lessonDao;
    @Autowired
    private IdHelper idHelper;



    @Override
    public Object action(BookQ bookQ) {
        Optional<CustomBean> customBeanOptional = customDao.find(bookQ.getCustomId());
        CustomBean customBean = customBeanOptional
                .orElseThrow(() -> new MicroServiceException(FitnessCode.CUSTOM_NOT_FUND));
        LocalDate today = LocalDate.now();
        String todayStr = today.format(df);
        if (todayStr.compareTo(customBean.getCardPeriod()) > 0){
            throw new MicroServiceException(FitnessCode.CARD_OVERDUE);
        }
        if (customBean.getUsableCount() == null || customBean.getUsableCount() < 1){
            throw new MicroServiceException(FitnessCode.CARD_EXHAUSTED);
        }
        Optional<LessonBean> lessonBeanOptional = lessonDao.find(bookQ.getLessonId());
        LessonBean lessonBean = lessonBeanOptional
                .orElseThrow(() -> new MicroServiceException(FitnessCode.LESSON_NOT_FUND));
        if (lessonBean.getBookCount() >= lessonBean.getPCount()){
            throw new MicroServiceException(FitnessCode.BOOKING_IS_FULL);
        }
        customBean.setUsableCount(customBean.getUsableCount() - 1);
        customBean.forUpdate();
        customDao.update(customBean);
        lessonBean.setBookCount(lessonBean.getBookCount() + 1);
        lessonBean.forUpdate();
        lessonDao.update(lessonBean);
        BookBean bookBean = new BookBean();
        bookBean.setCustomId(bookQ.getCustomId());
        bookBean.setLessonId(bookQ.getLessonId());
        bookBean.setId(idHelper.genId(FitnessConstant.ID_TYPE_BOOK));
        bookBean.forCreate();
        bookDao.insert(bookBean);
        bookBean.setLesson(lessonBean);
        return bookBean;
    }
}
