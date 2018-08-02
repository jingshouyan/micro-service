package io.jing.server.message.helper;

import io.jing.base.exception.MicroServiceException;
import io.jing.server.message.bean.Message;
import io.jing.server.message.constant.MessageCode;
import io.jing.server.message.constant.MessageConstant;
import io.jing.server.message.helper.related.Related;
import io.jing.server.message.helper.related.UserRelated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
public class RelatedProxy implements Related,MessageConstant {

    @Autowired
    private UserRelated userRelated;

    @Override
    public void actionBatch(Message message, Consumer<List<String>> consumer) {
        switch (message.getTargetType()){
            case MESSAGE_TARGET_TYPE_USER:
                userRelated.actionBatch(message,consumer);
                break;
            case MESSAGE_TARGET_TYPE_ROOM:

            default:
                throw new MicroServiceException(MessageCode.UNSUPPORTED_TARGET_TYPE);
        }
    }
}
