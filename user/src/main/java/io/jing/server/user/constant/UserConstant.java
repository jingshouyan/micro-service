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
    public static final String DS_DRIVER = ConfigSettings.get("datasource.driver").get();
    public static final String DS_USERNAME = ConfigSettings.get("datasource.username").get();
    public static final String DS_PASSWORD = ConfigSettings.get("datasource.password").get();
    public static final String DS_URL= ConfigSettings.get("datasource.url").get();
    public static final int WS_PORT = ConfigSettings.get("ws.port").map(Integer::parseInt).orElse(8888);
    public static final String WS_URI = "/ws";
    public static final String API_URI = "/api";
    public static final String RESOURCE_PATH = "META-INF/resources";
    public static final String Ticket = "ticket";
    public static final AttributeKey<Token> TOKEN_KEY = AttributeKey.valueOf("user.token");
}
