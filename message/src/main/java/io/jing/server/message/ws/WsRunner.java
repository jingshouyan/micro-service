package io.jing.server.message.ws;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import io.jing.server.message.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/5/31 13:28
 */
//@Component
@Slf4j
public class WsRunner implements CommandLineRunner {

    @Override
    public void run(String... strings) {
        Configuration configuration = new Configuration();
        configuration.setPort(MessageConstant.WS_PORT);
        configuration.setHostname("localhost");
        SocketIOServer server = new SocketIOServer(configuration);
        server.addConnectListener(client->{
            log.info("connected {}",client.getSessionId());
        });
        server.addDisconnectListener(client->{
            log.info("disconnected {}",client.getSessionId());
        });
        server.addEventListener("message",Object.class,(client,obj,ask)->{
            log.info("{} received {}",client.getSessionId(),obj);
            client.sendEvent("message",obj);
        });
        server.start();
    }

    public static void main(String... args) {
        WsRunner wsRunner = new WsRunner();
        wsRunner.run(args);
    }
}
