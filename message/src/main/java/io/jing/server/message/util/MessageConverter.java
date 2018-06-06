package io.jing.server.message.util;

import io.jing.base.util.json.JsonUtil;
import io.jing.server.message.bean.Message;
import io.jing.server.message.bean.MessageBean;
import io.jing.server.message.constant.MessageConstant;
import io.jing.util.jdbc.core.util.db.Bean4DbUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author jingshouyan
 * #date 2018/6/5 16:05
 */
@Slf4j
public class MessageConverter {

    public static Message toMessage(MessageBean messageBean){
        Message message = new Message();
        message.setId(messageBean.getMessageId());
        message.setSenderId(messageBean.getSenderId());
        message.setTargetId(messageBean.getTargetId());
        message.setTargetType(messageBean.getTargetType());
        message.setMessageType(messageBean.getMessageType());
        message.setRelatedUsers(messageBean.getRelatedUsers());
        if(messageBean.getFlag()!=null){
            message.setFlag(messageBean.getFlag());
        }
        if(messageBean.getSentAt()!=null){
            message.setSentAt(messageBean.getSentAt());
        }

        try{
            Field field = Bean4DbUtil.getBeanTable(Message.class)
                    .getFieldNameMap()
                    .get(messageBean.getMessageType()).getField();
            Type type = field.getGenericType();
            Object obj = JsonUtil.toBean(messageBean.getData(),type);
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
            field.set(message,obj);
        }catch (Exception e){
            log.warn("converter type[{}] data[{}] error"
                    ,messageBean.getMessageType(),messageBean.getData(),e);
        }
        return message;
    }



    public static MessageBean toMessageBean(String userId, Message message){
        MessageBean messageBean = new MessageBean();
        String messageBeanId = MessageUtil.messageBeanId(userId,message.getId());
        messageBean.setId(messageBeanId);
        messageBean.setUserId(userId);
        messageBean.setMessageId(message.getId());
        messageBean.setSenderId(message.getSenderId());
        messageBean.setTargetId(message.getTargetId());
        messageBean.setTargetType(message.getTargetType());
        messageBean.setMessageType(message.getMessageType());
        messageBean.setData(getData(message));
        messageBean.setFlag(message.getFlag());
        messageBean.setRelatedUsers(message.getRelatedUsers());
        messageBean.setSentAt(message.getSentAt());
        messageBean.setPush1(MessageConstant.MESSAGE_PUSH_NO);
        messageBean.setPush2(MessageConstant.MESSAGE_PUSH_NO);
        messageBean.setPush3(MessageConstant.MESSAGE_PUSH_NO);
        messageBean.setPush(MessageConstant.MESSAGE_PUSH_NO);
        messageBean.forCreate();
        return messageBean;
    }

    private static String getData(Message message){
        String messageType = message.getMessageType();
        Object data = Bean4DbUtil.getFieldValue(message,messageType);
        return JsonUtil.toJsonString(data);
    }

}
