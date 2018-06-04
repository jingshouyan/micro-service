package io.jing.server.message.constant;

import io.jing.base.util.config.ConfigSettings;
import io.jing.server.constant.ServerConstant;

/**
 * @author jingshouyan
 * #date 2018/5/30 16:58
 */
public interface MessageConstant extends ServerConstant {
    public static final String DS_DRIVER = ConfigSettings.get("datasource.driver").get();
    public static final String DS_USERNAME = ConfigSettings.get("datasource.username").get();
    public static final String DS_PASSWORD = ConfigSettings.get("datasource.password").get();
    public static final String DS_URL= ConfigSettings.get("datasource.url").get();
    public static final int WS_PORT = ConfigSettings.get("ws.port").map(Integer::parseInt).orElse(8888);
    public static final String HOST_NAME = ConfigSettings.get("host.name").orElse("localhost");
    public static final String EVENT_MESSAGE = "message";
    public static final String WS_STORE_TOKEN = "WS_STORE_TOKEN";
}
