package io.jing.server.user.constant;

import io.jing.base.bean.Token;
import io.jing.base.util.config.ConfigSettings;
import io.jing.server.constant.ServerConstant;
import io.netty.util.AttributeKey;

/**
 * @author jingshouyan
 * @date 2018/4/24 21:27
 */
public interface UserConstant extends ServerConstant {
    String DS_DRIVER = ConfigSettings.get("datasource.driver").get();
    String DS_USERNAME = ConfigSettings.get("datasource.username").get();
    String DS_PASSWORD = ConfigSettings.get("datasource.password").get();
    String DS_URL= ConfigSettings.get("datasource.url").get();
    int WS_PORT = ConfigSettings.get("ws.port").map(Integer::parseInt).orElse(8888);
    String WS_URI = "/ws";
    String API_URI = "/api";
    String RESOURCE_PATH = "META-INF/resources";
    String Ticket = "ticket";
    AttributeKey<Token> TOKEN_KEY = AttributeKey.valueOf("user.token");
}
