package io.jing.server.message.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import io.jing.server.message.constant.MessageConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public SocketIOServer socketIOServer(){
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setPort(MessageConstant.WS_PORT);
//        configuration.setHostname(MessageConstant.HOST_NAME);
        SocketIOServer socketIOServer = new SocketIOServer(configuration);
        return socketIOServer;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer server){
        return new SpringAnnotationScanner(server);
    }
}
