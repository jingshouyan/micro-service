package io.jing.server.message.method;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.SocketIOServer;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.jing.base.bean.Token;
import io.jing.server.message.bean.MessagePush;
import io.jing.server.message.constant.MessageConstant;
import io.jing.server.message.dao.MessageDao;
import io.jing.server.message.util.MessageUtil;
import io.jing.server.method.Method;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author jingshouyan
 * #date 2018/6/4 14:08
 */
@Component@Slf4j
public class PushMessage implements Method<MessagePush> , CommandLineRunner {

    private final BlockingQueue<MessageRes> queue = new LinkedBlockingDeque<>(10000);

    private final ExecutorService exec = new ThreadPoolExecutor(1, 10,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("update-push-pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy()
            );

    @Autowired
    private MessageDao messageDao;

    @Override
    public void run(String... strings) {
        exec.execute(()->{
            List<MessageRes> messageResList = Lists.newArrayList();
            while(true){
                try{
                    while (!queue.isEmpty()){
                        messageResList.add(queue.take());
                        if(messageResList.size()>=1000){
                            break;
                        }
                    }
                    Map<Integer,List<MessageRes>> map = messageResList.stream()
                            .collect(Collectors.groupingBy(MessageRes::getClientType));
                    map.forEach((clientType,list)->{
                        List<String> idList = list.stream().map(MessageRes::getId).collect(Collectors.toList());
                        messageDao.updatePush(idList,clientType);
                    });
                    messageResList.clear();
                    messageResList.add(queue.take());
                }catch (Exception e){
                    log.warn("message res to db error",e);
                }

            }
        });
    }

    @Autowired
    SocketIOServer server;
    @Override
    public Object action(MessagePush messagePush) {

        messagePush.getConnIdList().stream()
                .map(UUID::fromString)
                .map(server::getClient)
                .filter(Objects::nonNull)
                .forEach(client -> {
                    //TODO: 更新已送达
                    Token token = client.get(MessageConstant.WS_STORE_TOKEN);
                    client.sendEvent(MessageConstant.EVENT_MESSAGE,
                        new AckCallback<Object>(Object.class) {
                            @Override
                            public void onSuccess(Object o) {
                                String messageBeanId = MessageUtil.messageBeanId(token.getUserId(),messagePush.getMessage().getId());
                                MessageRes res = new MessageRes();
                                res.setId(messageBeanId);
                                res.setClientType(token.getClientType());
                                queue.offer(res);
                            }
                        },
                        messagePush.getMessage());
                    log.info("message push :connId:[{}],token[{}],message:[{}]",client.getSessionId(),token,messagePush.getMessage());
                });
        return null;
    }



    @Data
    private static class MessageRes{
        private String id;
        private int clientType;
    }
}
