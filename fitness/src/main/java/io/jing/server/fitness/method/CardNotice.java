package io.jing.server.fitness.method;

import io.jing.server.fitness.bean.CustomBean;
import io.jing.server.fitness.bean.NoticeInfo;
import io.jing.server.fitness.dao.CustomDao;
import io.jing.server.method.Method;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.db.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component("fitness.birthNotice")
@Slf4j
public class CardNotice implements Method<NoticeInfo> {


    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private CustomDao customDao;

    @Override
    public Object action(NoticeInfo noticeInfo) {
        LocalDate today = LocalDate.now();
        LocalDate day = today.minusDays(noticeInfo.getDays());
        String end = today.format(df);
        String start = day.format(df);
        log.info("start: {}, end: {}",start,end);
        List<Compare> compares = CompareUtil.newInstance()
                .field("cardPeriod").gte(start).lte(end)
                .compares();
        Page<CustomBean> page = new Page<>();
        page.addOrderBy("cardPeriod",false);
        page.setPageSize(100);
        return customDao.queryLimit(compares,page);
    }
}
