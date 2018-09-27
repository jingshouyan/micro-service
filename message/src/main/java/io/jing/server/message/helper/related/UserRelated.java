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
        List<String> userIds = Lists.newArrayList();
        userIds.add(message.getSenderId());
        if(!message.selfMessage()){
            userIds.add(message.getTargetId());
        }
        consumer.accept(userIds);
    }
}
