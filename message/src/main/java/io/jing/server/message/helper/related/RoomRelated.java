package io.jing.server.message.helper.related;

import com.google.common.collect.Maps;
import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.util.code.Code;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.client.util.ClientUtil;
import io.jing.server.message.bean.Message;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class RoomRelated implements Related {
    public static final int BATCH_SIZE = 200;
    @Override
    public void actionBatch(Message message, Consumer<List<String>> c) {
        Token token = ThreadLocalUtil.getToken();
        long revision = 0;
        while(true){
            Map<String,Object> param = Maps.newHashMap();
            param.put("revision",revision);
            param.put("size",BATCH_SIZE);
            param.put("containDel",false);
            Req req = Req.builder().service("relationship")
                    .method("listRoomUser").paramObj(param).build();
            Rsp rsp = ClientUtil.call(token,req);
            if(rsp.getCode() != Code.SUCCESS){
                break;
            }
            List<RoomUser> roomUsers = rsp.list(RoomUser.class);
            revision = roomUsers.stream().mapToLong(RoomUser::getRevisionUser).max().orElse(0L);
            List<String> userIds = roomUsers.stream().map(RoomUser::getUserId).collect(Collectors.toList());
            c.accept(userIds);
            if(roomUsers.size()< BATCH_SIZE){
                break;
            }
        }

    }

    @Getter@Setter
    private static class RoomUser {
        private String userId;
        private long revisionUser;
    }
}
