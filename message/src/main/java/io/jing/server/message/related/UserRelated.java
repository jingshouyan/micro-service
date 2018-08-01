package io.jing.server.message.related;

import com.google.common.collect.Lists;
import io.jing.server.message.bean.Message;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserRelated implements Related{

    @Override
    public void r(Message message, Function<List<String>, Boolean> function) {
        List<String> userIdList = Lists.newArrayList();
        userIdList.add(message.getSenderId());
        if(!message.selfMessage()){
            userIdList.add(message.getTargetId());
        }
        if(message.getRelatedUsers()!=null && !message.getRelatedUsers().isEmpty()){
            userIdList = userIdList.stream()
                    .filter(message.getRelatedUsers()::contains)
                    .collect(Collectors.toList());
        }
        function.apply(userIdList);
    }
}
