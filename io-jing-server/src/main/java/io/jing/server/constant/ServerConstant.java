package io.jing.server.constant;

import io.jing.base.constant.BaseConstant;
import io.jing.base.util.config.ConfigSettings;



/**
 * @author jingshouyan
 * @date 2018/4/16 15:22
 */
public interface ServerConstant extends BaseConstant {
    int THRIFT_SERVER_PORT_MIN = 11000;
    int THRIFT_SERVER_PORT_MAX = 12000;

    int THRIFT_SERVER_PORT = ConfigSettings.get("thrift.server.port").map(Integer::parseInt).orElse(0);
    String THRIFT_SERVER_NAME = ConfigSettings.get("thrift.server.name").orElse("microService");
    String THRIFT_SERVER_VERSION = ConfigSettings.get("thrift.server.version").orElse("1.0");
    String THRIFT_SERVER_IP = ConfigSettings.get("thrift.server.ip").orElse("127.0.0.1");
    boolean SQL_LOG_SHOW = ConfigSettings.get("sql.log.show").filter("true"::equalsIgnoreCase).isPresent();
}
