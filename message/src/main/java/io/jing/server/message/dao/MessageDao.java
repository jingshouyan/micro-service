package io.jing.server.message.dao;

import io.jing.server.message.bean.MessageBean;
import io.jing.util.jdbc.core.dao.BaseDao;

import java.util.List;

/**
 * @author jingshouyan
 * #date 2018/5/30 16:41
 */
public interface MessageDao extends BaseDao<MessageBean> {

    int updatePush(List<String> idList,int clientType);

    List<MessageBean> nonPushList(String userId, int clientType, long latestMessageId);
}
