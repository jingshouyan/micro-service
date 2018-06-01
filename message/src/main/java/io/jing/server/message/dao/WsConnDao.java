package io.jing.server.message.dao;

import io.jing.server.message.bean.WsConnBean;
import io.jing.util.jdbc.core.dao.BaseDao;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/5/30 17:40
 */
public interface WsConnDao extends BaseDao<WsConnBean> {
    List<WsConnBean> listByTokenId(String tokenId);

    List<WsConnBean> listByUserIdList(List<String> userIdList);
}
