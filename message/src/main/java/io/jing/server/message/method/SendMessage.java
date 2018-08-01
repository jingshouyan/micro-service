package io.jing.server.message.method;

import com.google.common.collect.Lists;
import io.jing.base.bean.Req;
import io.jing.base.bean.Token;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.client.util.ClientUtil;
import io.jing.server.db.helper.IdHelper;
import io.jing.server.message.bean.Message;
import io.jing.server.message.bean.MessageBean;
import io.jing.server.message.bean.MessagePush;
import io.jing.server.message.bean.WsConnBean;
import io.jing.server.message.cache.WsConnCache;
import io.jing.server.message.constant.MessageConstant;
import io.jing.server.message.dao.MessageDao;
import io.jing.server.message.util.MessageConverter;
import io.jing.server.method.Method;
import io.jing.server.zk.Register;
import io.jing.util.jdbc.core.util.keygen.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jingshouyan
 * #date 2018/6/1 13:18
 */
@Component@Slf4j
public class SendMessage implements Method<Message> {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private PushMessage pushMessage;

    @Autowired
    private WsConnCache wsConnCache;

    @Autowired
    private IdHelper idHelper;


    @Override
    public Object action(Message message) {
        message.setId(idHelper.genId(MessageConstant.ID_TYPE_MESSAGE));
        message.setSenderId(ThreadLocalUtil.userId());
        String ticket = ThreadLocalUtil.ticket();
        int clientType = ThreadLocalUtil.getToken().getClientType();
        message.setSentAt(System.currentTimeMillis());
        List<String> userIdList = relatedUserId(message);
        if(!userIdList.isEmpty()){
            List<MessageBean> messageBeanList = userIdList.stream()
                    .map(userId -> MessageConverter.toMessageBean(userId,message))
                    .peek(messageBean -> {
                        if(messageBean.getUserId().equals(messageBean.getSenderId())){
                            switch (clientType){
                                case 1:
                                    messageBean.setPush1(MessageConstant.MESSAGE_PUSH_YES);
                                    break;
                                case 2:
                                    messageBean.setPush2(MessageConstant.MESSAGE_PUSH_YES);
                                    break;
                                case 3:
                                    messageBean.setPush3(MessageConstant.MESSAGE_PUSH_YES);
                                    break;
                            }
                            messageBean.setPush(MessageConstant.MESSAGE_PUSH_YES);
                        }
                    })
                    .collect(Collectors.toList());
            messageDao.batchInsert(messageBeanList);
            List<WsConnBean> wsConnBeanList = wsConnCache.getByUserIds(userIdList);
            Map<String,List<WsConnBean>> map = wsConnBeanList.stream()
                    .filter(wsConnBean -> !wsConnBean.getTicket().equals(ticket))
                    .collect(Collectors.groupingBy(WsConnBean::getServiceInstance));
            String thisInstance = Register.SERVICE_INSTANCE.key();
            map.forEach((serviceInstance,connList)->{
                List<String> conns = connList.stream()
                        .map(WsConnBean::getId)
                        .collect(Collectors.toList());
                MessagePush messagePush = new MessagePush();
                messagePush.setMessage(message);
                messagePush.setConnIdList(conns);
                if(serviceInstance.equals(thisInstance)){
                    try{
                        pushMessage.actionWithValidate(messagePush);
                    }catch (Exception e){
                        log.warn("pushMessage error.",e);
                    }
                }else{
                    Token token = ThreadLocalUtil.getToken();
                    Req req = Req.builder()
                            .service(MessageConstant.THRIFT_SERVER_NAME)
                            .router(serviceInstance)
                            .oneWay(true)
                            .method("PushMessage")
                            .paramObj(messagePush)
                            .build();
                    ClientUtil.call(token,req);
                }
            });
        }
        return message;
    }

    private List<String> relatedUserId(Message message){
        List<String> userIdList = Lists.newArrayList();
        switch (message.getTargetType()){
            case "user":
                userIdList.add(message.getSenderId());
                if(!message.selfMessage()){
                    userIdList.add(message.getTargetId());
                }
                break;
            default:
                log.warn("Unsupport targetType[{}]",message.getTargetType());
                break;
        }
        if(message.getRelatedUsers()!=null && !message.getRelatedUsers().isEmpty()){
            userIdList = userIdList.stream()
                    .filter(message.getRelatedUsers()::contains)
                    .collect(Collectors.toList());
        }
        return userIdList;
    }



}
