package io.jing.server.message.ws;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import io.jing.server.message.constant.MessageConstant;

public class ChatLauncher {

    public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(MessageConstant.WS_PORT);

        final SocketIOServer server = new SocketIOServer(config);
        server.addConnectListener(client->{
            String token = client.getHandshakeData().getSingleUrlParam("token");
            System.out.println("token:"+token);
//            client.disconnect();
            client.sendEvent("eee","desconnect");
            System.out.println("client "+client.getSessionId()+" disconnect");
        });
        server.addEventListener("message", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
                // broadcast messages to all clients
                System.out.println(client.getSessionId()+" ----> "+data);
                server.getBroadcastOperations().sendEvent("message", data);
            }
        });

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
