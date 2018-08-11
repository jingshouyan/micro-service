package io.jing.server.user.method;

import io.jing.server.method.Method;
import io.jing.server.user.bean.SearchReq;
import io.jing.server.user.bean.UserBean;
import io.jing.server.user.dao.UserDao;
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
@Component("user.searchUser")
public class SearchUser implements Method<SearchReq> {

    @Autowired
    private UserDao userDao;

    @Override
    public Object action(SearchReq queryBean) {
        List<Compare> compares = CompareUtil.newInstance()
                .field("nickname").like("%"+queryBean.getQ()+"%")
                .field("deletedAt").eq(-1)
                .compares();
        Page<UserBean> page = new Page<>();
        page.setPage(queryBean.getPage());
        page.setPageSize(queryBean.getSize());
        return userDao.query(compares,page);
    }
}
