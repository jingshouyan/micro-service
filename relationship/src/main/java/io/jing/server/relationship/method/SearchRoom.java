package io.jing.server.relationship.method;

import io.jing.server.method.Method;

import io.jing.server.relationship.bean.RoomBean;
import io.jing.server.relationship.bean.SearchReq;
import io.jing.server.relationship.dao.RoomDao;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.db.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/6/16 16:23
 */
@Component
public class SearchRoom implements Method<SearchReq> {

    @Autowired
    private RoomDao roomDao;

    @Override
    public Object action(SearchReq queryBean) {
        List<Compare> compares = CompareUtil.newInstance()
                .field("name").like("%"+queryBean.getQ()+"%")
                .field("deleteAt").eq(-1)
                .compares();
        Page<RoomBean> page = new Page<>();
        page.setPage(queryBean.getPage());
        page.setPageSize(queryBean.getSize());
        page = roomDao.query(compares,page);
        return page;
    }
}
