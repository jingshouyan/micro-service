package io.jing.server.message.ws;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.jing.base.bean.Token;
import io.jing.base.thrift.MicroService;
import io.jing.base.util.thread.ExecUtil;
import io.jing.server.message.bean.Message;
import io.jing.server.message.bean.MessageBean;
import io.jing.server.message.bean.MessagePush;
import io.jing.server.message.bean.WsConnBean;
import io.jing.server.message.cache.WsConnCache;
import io.jing.server.message.constant.MessageConstant;
import io.jing.server.message.dao.MessageDao;
import io.jing.server.message.dao.WsConnDao;
import io.jing.server.message.method.PushMessage;
import io.jing.server.message.util.MessageConverter;
import io.jing.server.zk.Register;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author jingshouyan
 * #date 2018/6/4 19:32
 */
@Component@Slf4j
public class ConnectionHandler  implements CommandLineRunner {
    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private WsConnDao wsConnDao;

    @Autowired
    private WsConnCache wsConnCache;

    @Autowired
    private PushMessage pushMessage;

    @Autowired
    private MessageDao messageDao;

    private final DelayQueue<ConnDelayed> delayQueue = new DelayQueue<>();

    private final ExecutorService exec = new ThreadPoolExecutor(10, 30,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("conn-pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy()
    );
    private final ExecutorService exec2 = new ThreadPoolExecutor(1, 10,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("delay-queue-%d").build(),
            new ThreadPoolExecutor.AbortPolicy()
    );

    @OnConnect
    public void onConnect(SocketIOClient client){
        String tokenId = client.getHandshakeData().getSingleUrlParam("token");
        String connId = client.getSessionId().toString();

        //TODO: check token
        if(tokenId == null){
            client.disconnect();
            return;
        }
        log.info("token[{}],sessionId[{}] connected.",tokenId,connId);
        Token token = Token.builder().ticket(tokenId).userId(tokenId).clientType(1).build();
        client.set(MessageConstant.WS_STORE_TOKEN,token);
        List<WsConnBean> oldList = wsConnDao.listByTokenId(token.getTicket());
        if(!oldList.isEmpty()){
            List<String> idList = oldList.stream()
                    .map(WsConnBean::getId)
                    .collect(Collectors.toList());
            wsConnDao.delete4List(idList);
            idList.stream().map(UUID::fromString)
                    .forEach(uuid->{
                        SocketIOClient oldClient = socketIOServer.getClient(uuid);
                        if(oldClient!=null){
                            log.info("sessionId[{}] kicked",uuid);
                            oldClient.disconnect();
                        }
                    });
        }
        delayQueue.put(new ConnDelayed(connId,MessageConstant.CONN_HANDLE_DELAY));


    }



    @OnDisconnect
    public void onDisconnect(SocketIOClient client){
        String id = client.getSessionId().toString();
        log.info("sessionId[{}] disconnect",id);
        wsConnDao.delete(id);
        Token token = client.get(MessageConstant.WS_STORE_TOKEN);
        wsConnCache.removeByUserId(token.getUserId());
    }

    @Override
    public void run(String... strings) {
        exec2.execute(()->{
            while (true){
                try{
                    ConnDelayed connDelayed = delayQueue.take();
                    SocketIOClient client = socketIOServer.getClient(UUID.fromString(connDelayed.getConnId()));
                    if(client == null){
                        continue;
                    }
                    Token token = client.get(MessageConstant.WS_STORE_TOKEN);
                    if(null!=client&&client.isChannelOpen()){
                        ExecUtil.exec(exec,()->{
                            long latestId = 0;
                            while(true){
                                List<MessageBean> list = messageDao.nonPushList(token.getUserId(),1,latestId);
                                for (MessageBean messageBean: list) {
                                    Message message = MessageConverter.toMessage(messageBean);
                                    MessagePush messagePush = new MessagePush();
                                    messagePush.setMessage(message);
                                    messagePush.setConnIdList(Lists.newArrayList(connDelayed.getConnId()));
                                    pushMessage.actionWithValidate(messagePush);
                                    latestId = message.getId();
                                }
                                if(list.size()<MessageConstant.NON_PUSH_MESSAGE_FITCH_SIZE){
                                    break;
                                }
                            }
                            WsConnBean wsConnBean = new WsConnBean();
                            wsConnBean.setId(client.getSessionId().toString());
                            wsConnBean.setClientType(1);
                            wsConnBean.setTicket(token.getTicket());
                            wsConnBean.setUserId(token.getUserId());
                            wsConnBean.setServiceInstance(Register.SERVICE_INSTANCE.key());
                            wsConnBean.forCreate();
                            wsConnDao.insert(wsConnBean);
                            wsConnCache.removeByUserId(token.getUserId());
                        });
                    }
                }catch (Exception e){
                }
            }
        });
    }

    private static class ConnDelayed implements Delayed {
        private long runAt;
        @Getter
        private String connId;

        public ConnDelayed(String connId,long delay){
            this.connId = connId;
            this.runAt = System.currentTimeMillis() + delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return this.runAt - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            long delayB = o.getDelay(timeUnit);
            long delayA = this.getDelay(timeUnit);
            if(delayA>delayB){
                return 1;
            }else if(delayA == delayB){
                return 0;
            }
            return -1;
        }

    }
}
