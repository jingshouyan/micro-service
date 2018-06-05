package io.jing.server.message.ws;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import io.jing.base.bean.Token;
import io.jing.server.message.bean.WsConnBean;
import io.jing.server.message.cache.WsConnCache;
import io.jing.server.message.constant.MessageConstant;
import io.jing.server.message.dao.WsConnDao;
import io.jing.server.zk.Register;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author jingshouyan
 * #date 2018/6/4 19:32
 */
@Component@Slf4j
public class ConnectionHandler {
    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private WsConnDao wsConnDao;

    @Autowired
    private WsConnCache wsConnCache;

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
        wsConnCache.removeByUserId(token.getUserId());
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client){
        String id = client.getSessionId().toString();
        log.info("sessionId[{}] disconnect",id);
        wsConnDao.delete(id);
        Token token = client.get(MessageConstant.WS_STORE_TOKEN);
        wsConnCache.removeByUserId(token.getUserId());
    }
}
