package io.jing.server.message.ws;

import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.jing.base.bean.Req;
import io.jing.base.bean.Token;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.iface.MicroServiceImpl;
import io.jing.server.message.bean.TokenBean;
import io.jing.server.message.bean.WsConnBean;
import io.jing.server.message.constant.MessageConstant;
import io.jing.server.message.dao.WsConnDao;
import io.jing.server.message.bean.Message;
import io.jing.server.message.method.SendMessage;
import io.jing.server.zk.Register;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jingshouyan
 * #date 2018/5/30 18:51
 */
@Component
@Slf4j
public class MessageEventHandler {

    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private WsConnDao wsConnDao;

    @Autowired
    private SendMessage sendMessage;

    @Autowired
    private MicroServiceImpl microService;

    @OnConnect
    public void onConnect(SocketIOClient client){
        String tokenId = client.getHandshakeData().getSingleUrlParam("token");
        String connId = client.getSessionId().toString();

        //TODO: check token
        if(tokenId == null){
            client.disconnect();
            return;
        }

//        client.set();
//        WsConnBean wsConn = wsConnDao.find(connId);
//        if(null != wsConn){
//            // reconnect
//            if(tokenId.equals(wsConn.getTokenId())){
//                // same token ,do nothing
//                log.info("token[{}],sessionId[{}] reconnected.",tokenId,connId);
//            }else {
//                log.info("token[{}],sessionId[{}] reconnected.old:{}",tokenId,connId,wsConn.getTokenId());
//                wsConn.setTokenId(tokenId);
//                wsConn.setUserId(tokenId);
//                wsConn.setClientType(1);
//                wsConn.forUpdate();
//                wsConnDao.update(wsConn);
//            }
//            return;
//        }
        log.info("token[{}],sessionId[{}] connected.",tokenId,connId);
        List<WsConnBean> oldList = wsConnDao.listByTokenId(tokenId);
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
        WsConnBean wsConnBean = new WsConnBean();
        wsConnBean.setId(client.getSessionId().toString());
        wsConnBean.setClientType(1);
        wsConnBean.setTokenId(tokenId);
        wsConnBean.setUserId(tokenId);
        wsConnBean.setServiceInstance(Register.SERVICE_INSTANCE.key());
        wsConnBean.forCreate();
        wsConnDao.insert(wsConnBean);
        Token token = Token.builder().ticket(tokenId).userId(tokenId).build();
        client.set(MessageConstant.WS_STORE_TOKEN,token);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client){
        String id = client.getSessionId().toString();
        log.info("sessionId[{}] disconnect",id);
        wsConnDao.delete(id);
    }

    @OnEvent(value = "message")
    public void onMessageEvent(SocketIOClient client, AckRequest request,Message message){
        ThreadLocalUtil.setTraceId(message.getSenderId());
        ThreadLocalUtil.setToken(client.get(MessageConstant.WS_STORE_TOKEN));

        log.info("message:{}",message);
//        client.sendEvent("message", new VoidAckCallback() {
//            @Override
//            protected void onSuccess() {
//                log.info("send success . {}",client.getSessionId());
//            }
//        }, message);

        Token token = ThreadLocalUtil.getToken();
        Req req = Req.builder()
                .service(MessageConstant.THRIFT_SERVER_NAME)
                .oneWay(true)
                .method("SendMessage")
                .paramObj(message)
                .build();

        microService.run(token,req);
        ThreadLocalUtil.removeToken();
        ThreadLocalUtil.removeTrace();
    }
}
