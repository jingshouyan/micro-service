package io.jing.server.message.helper.related;

import com.google.common.collect.Lists;
import io.jing.server.message.bean.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
public class UserRelated implements Related{

    @Override
    public void actionBatch(Message message, Consumer<List<String>> consumer) {
        List<String> userIdList = Lists.newArrayList();
        userIdList.add(message.getSenderId());
        if(!message.selfMessage()){
            userIdList.add(message.getTargetId());
        }
        userIdList = filterRelated(message,userIdList);
        if(!userIdList.isEmpty()){
            consumer.accept(userIdList);
        }
    }
}
