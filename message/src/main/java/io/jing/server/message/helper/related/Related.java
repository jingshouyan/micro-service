package io.jing.server.message.related;

import io.jing.server.message.bean.Message;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Related {

    void r(Message message, Function<List<String>,Boolean> function);

    default List<String> filterRelated(Message message,List<String> userIdList){
        if(message.getRelatedUsers()!=null && !message.getRelatedUsers().isEmpty()){
            userIdList = userIdList.stream()
                    .filter(message.getRelatedUsers()::contains)
                    .collect(Collectors.toList());
        }
        return userIdList;
    }
}
