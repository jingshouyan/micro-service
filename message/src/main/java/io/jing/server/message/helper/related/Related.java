package io.jing.server.message.helper.related;

import io.jing.server.message.bean.Message;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 */
public interface Related {

    void actionBatch(Message message, Consumer<List<String>> c);

    default List<String> filterRelated(Message message,List<String> userIds){
        if(message.getRelatedUsers()!=null && !message.getRelatedUsers().isEmpty()){
            userIds = userIds.stream()
                    .filter(message.getRelatedUsers()::contains)
                    .collect(Collectors.toList());
        }
        return userIds;
    }
}
