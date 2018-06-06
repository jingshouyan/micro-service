package io.jing.server.message.constant;

import io.jing.base.util.config.ConfigSettings;
import io.jing.server.constant.ServerConstant;

/**
 * @author jingshouyan
 * #date 2018/5/30 16:58
 */
public interface MessageConstant extends ServerConstant {
    String DS_DRIVER = ConfigSettings.get("datasource.driver").get();
    String DS_USERNAME = ConfigSettings.get("datasource.username").get();
    String DS_PASSWORD = ConfigSettings.get("datasource.password").get();
    String DS_URL= ConfigSettings.get("datasource.url").get();
    int WS_PORT = ConfigSettings.get("ws.port").map(Integer::parseInt).orElse(8888);
    String HOST_NAME = ConfigSettings.get("host.name").orElse("localhost");
    String EVENT_MESSAGE = "message";
    String WS_STORE_TOKEN = "WS_STORE_TOKEN";
    int MESSAGE_PUSH_YES = 1;
    int MESSAGE_PUSH_NO = 2;
    int CLIENT_TYPE_1= 1;
    int CLIENT_TYPE_2= 2;
    int CLIENT_TYPE_3= 3;

    int NON_PUSH_MESSAGE_FITCH_SIZE = 2000;

    long CONN_HANDLE_DELAY = 1000;
}
