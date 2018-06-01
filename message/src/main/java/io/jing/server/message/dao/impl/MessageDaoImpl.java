package io.jing.server.message.dao.impl;

import io.jing.server.message.bean.MessageBean;
import io.jing.server.message.dao.MessageDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * @author jingshouyan
 * #date 2018/5/30 16:42
 */
@Repository
public class MessageDaoImpl extends BaseDaoImpl<MessageBean> implements MessageDao {
}
