package io.jing.server.message.dao.impl;

import io.jing.server.message.bean.WsConnBean;
import io.jing.server.message.dao.WsConnDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/5/30 17:40
 */
@Repository
public class WsConnDaoImpl extends BaseDaoImpl<WsConnBean> implements WsConnDao {
    @Override
    public List<WsConnBean> listByTicket(String tokenId){
        List<Compare> compares = CompareUtil.newInstance()
                .field("ticket").eq(tokenId).compares();
        List<WsConnBean> wsConnBeans = query(compares);
        return wsConnBeans;
    }

    @Override
    public List<WsConnBean> listByUserIdList(List<String> userIdList) {
        List<Compare> compares = CompareUtil.newInstance()
                .field("userId").in(userIdList).compares();
        List<WsConnBean> wsConnBeans = query(compares);
        return wsConnBeans;
    }
}
