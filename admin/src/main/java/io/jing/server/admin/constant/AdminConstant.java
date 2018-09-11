package io.jing.server.admin.constant;

import io.jing.base.bean.Token;
import io.jing.base.util.config.ConfigSettings;
import io.jing.server.constant.ServerConstant;

/**
 * @author jingshouyan
 * @date 2018/4/24 21:27
 */
public interface AdminConstant extends ServerConstant {
    int WS_PORT = ConfigSettings.get("ws.port").map(Integer::parseInt).orElse(8888);
    String WS_URI = "/ws";
    String API_URI = "/api";
    String RESOURCE_PATH = "META-INF/resources";
    String Ticket = "ticket";

    String ID_TYPE_USER = "user";
}
