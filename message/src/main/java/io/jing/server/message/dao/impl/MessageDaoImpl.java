package io.jing.server.message.dao.impl;

import io.jing.server.message.bean.MessageBean;
import io.jing.server.message.constant.MessageConstant;
import io.jing.server.message.dao.MessageDao;
import io.jing.util.jdbc.core.dao.impl.BaseDaoImpl;
import io.jing.util.jdbc.core.util.db.Compare;
import io.jing.util.jdbc.core.util.db.CompareUtil;
import io.jing.util.jdbc.core.util.db.Page;
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

    @Override
    public List<MessageBean> nonPushList(String userId, int clientType, long latestMessageId){
        List<Compare> compares = CompareUtil.newInstance()
                .field("userId").eq(userId)
                .field("messageId").gt(latestMessageId)
                .compares();
        Compare compare = new Compare();
        switch (clientType){
            case MessageConstant.CLIENT_TYPE_1:
                compare.setField("push1");
                break;
            case MessageConstant.CLIENT_TYPE_2:
                compare.setField("push2");
                break;
            case MessageConstant.CLIENT_TYPE_3:
                compare.setField("push3");
                break;
            default:
                compare.setField("push");
                break;
        }
        compare.setEq(MessageConstant.MESSAGE_PUSH_NO);
        compares.add(compare);
        Page<MessageBean> page = new Page<>();
        page.setPage(1);
        page.setPageSize(MessageConstant.NON_PUSH_MESSAGE_FITCH_SIZE);
        page.addOrderBy("messageId",true);
        return queryLimit(compares,page);
    }
}
