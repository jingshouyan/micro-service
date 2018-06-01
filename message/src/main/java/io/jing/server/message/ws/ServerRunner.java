package io.jing.server.message.ws;

import com.corundumstudio.socketio.SocketIOServer;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * #date 2018/5/30 18:12
 */
@Component
@Slf4j
public class ServerRunner implements CommandLineRunner {

    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private MessageEventHandler messageEventHandler;

    @Autowired
    public ServerRunner(SocketIOServer socketIOServer){
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void run(String... strings) {
        log.info("{}",Lists.newArrayList(strings));
//        socketIOServer.addListeners(messageEventHandler);
        socketIOServer.start();
    }
}
