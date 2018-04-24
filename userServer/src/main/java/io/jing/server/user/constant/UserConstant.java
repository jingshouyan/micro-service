package io.jing.server.user.constant;

import io.jing.base.util.config.ConfigSettings;
import io.jing.server.constant.ServerConstant;

/**
 * @author jingshouyan
 * @date 2018/4/24 21:27
 */
public interface UserConstant extends ServerConstant {
    public static final String DS_DRIVER = ConfigSettings.get("datasource.driver").get();
    public static final String DS_USERNAME = ConfigSettings.get("datasource.username").get();
    public static final String DS_PASSWORD = ConfigSettings.get("datasource.password").get();
    public static final String DS_URL= ConfigSettings.get("datasource.url").get();
}
