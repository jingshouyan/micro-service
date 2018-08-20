package io.jing.server.message.ws;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.util.threadlocal.ThreadLocalUtil;
import io.jing.server.iface.MicroServiceImpl;
import io.jing.server.message.bean.Message;
import io.jing.server.message.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/5/30 18:51
 */
@Component
@Slf4j
public class MessageEventHandler {

    @Autowired
    private MicroServiceImpl microService;

    @OnEvent(value = "message")
    public void onMessageEvent(SocketIOClient client, AckRequest request,Message message){
        ThreadLocalUtil.setTraceId(message.getSenderId());
        ThreadLocalUtil.setToken(client.get(MessageConstant.WS_STORE_TOKEN));

        log.info("message:{}",message);
        Token token = ThreadLocalUtil.getToken();
        Req req = Req.builder()
                .service(MessageConstant.THRIFT_SERVER_NAME)
                .oneWay(false)
                .method("sendMessage")
                .paramObj(message)
                .build();

        Rsp rsp = microService.run(token,req);
        request.sendAckData(rsp);
        ThreadLocalUtil.removeToken();
        ThreadLocalUtil.removeTrace();
    }
}
