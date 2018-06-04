package io.jing.server.message.method;

import com.corundumstudio.socketio.SocketIOServer;
import io.jing.server.message.bean.MessagePush;
import io.jing.server.message.constant.MessageConstant;
import io.jing.server.method.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jingshouyan
 * #date 2018/6/4 14:08
 */
@Component@Slf4j
public class PushMessage implements Method<MessagePush> {
    @Autowired
    SocketIOServer server;
    @Override
    public Object action(MessagePush messagePush) {
        messagePush.getConnIdList().stream()
                .map(UUID::fromString)
                .map(server::getClient)
                .filter(client -> client != null)
                .forEach(client -> client.sendEvent(MessageConstant.EVENT_MESSAGE,messagePush.getMessage()));
        return null;
    }
}
