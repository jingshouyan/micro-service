package io.jing.server.message.dao.impl;

import io.jing.server.message.bean.MessageBean;
import io.jing.server.message.constant.MessageConstant;
import io.jing.server.message.dao.MessageDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author jingshouyan
 * #date 2018/5/30 16:42
 */
@Repository
public class MessageDaoImpl extends BaseDaoImpl<MessageBean> implements MessageDao {
    @Override
    public int updatePush(List<String> idList, int clientType){
        MessageBean bean4Update = new MessageBean();
        bean4Update.setPush(MessageConstant.MESSAGE_PUSH_YES);
        switch (clientType){
            case MessageConstant.CLIENT_TYPE_1:
                bean4Update.setPush1(MessageConstant.MESSAGE_PUSH_YES);
                break;
            case MessageConstant.CLIENT_TYPE_2:
                bean4Update.setPush2(MessageConstant.MESSAGE_PUSH_YES);
                break;
            case MessageConstant.CLIENT_TYPE_3:
                bean4Update.setPush3(MessageConstant.MESSAGE_PUSH_YES);
                break;
            default:
                return 0;
        }
        List<Compare> compares = CompareUtil.newInstance().field("id").in(idList).compares();
        return update(bean4Update,compares);
    }
}
