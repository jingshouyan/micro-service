package io.jing.server.message.method;

import com.google.common.collect.Lists;
import io.jing.base.bean.Req;
import io.jing.base.bean.Token;
import io.jing.base.util.json.JsonUtil;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.client.util.ClientUtil;
import io.jing.server.iface.MicroServiceImpl;
import io.jing.server.message.bean.Message;
import io.jing.server.message.bean.MessageBean;
import io.jing.server.message.bean.MessagePush;
import io.jing.server.message.bean.WsConnBean;
import io.jing.server.message.constant.MessageConstant;
import io.jing.server.message.dao.MessageDao;
import io.jing.server.message.dao.WsConnDao;
import io.jing.server.method.Method;
import io.jing.server.zk.Register;
import io.jing.util.jdbc.core.util.db.Bean4DbUtil;
import io.jing.util.jdbc.core.util.keygen.IdUtil;
import lombok.SneakyThrows;
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
    private WsConnDao wsConnDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private PushMessage pushMessage;


    @Override
    public Object action(Message message) {
        message.setId(IdUtil.longId());
        message.setSenderId(ThreadLocalUtil.getToken().getUserId());
        message.setSentAt(System.currentTimeMillis());
        List<String> userIdList = relatedUserId(message);
        if(!userIdList.isEmpty()){
            List<MessageBean> messageBeanList = userIdList.stream()
                    .map(userId -> toMessageBean(userId,message)).collect(Collectors.toList());
            messageDao.batchInsert(messageBeanList);
            List<WsConnBean> wsConnBeanList = wsConnDao.listByUserIdList(userIdList);
            Map<String,List<WsConnBean>> map = wsConnBeanList.stream()
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
        return null;
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
                    log.warn("Unsupport targetType[{}]",message.getMessageType());
                    break;
        }
        return userIdList;
    }

    private MessageBean toMessageBean(String userId,Message message){
        MessageBean messageBean = new MessageBean();
        messageBean.setUserId(userId);
        messageBean.setMessageId(message.getId());
        messageBean.setSenderId(message.getSenderId());
        messageBean.setTargetId(message.getTargetId());
        messageBean.setTargetType(message.getTargetType());
        messageBean.setMessageType(message.getMessageType());
        messageBean.setData(getData(message));
        messageBean.setFlag(message.getFlag());
        messageBean.setSentAt(message.getSentAt());
        messageBean.forCreate();
        return messageBean;
    }

    private String getData(Message message){
        String messageType = message.getMessageType();
        Object data = Bean4DbUtil.getFieldValue(message,messageType);
        return JsonUtil.toJsonString(data);
    }

}
