package io.jing.base.constant;

import io.jing.base.util.config.ConfigSettings;

/**
 * @author jingshouyan
 * @date 2018/4/17 22:15
 */
public interface BaseConstant {
    String ZK_BASE_PATH = ConfigSettings.get("zk.base.path").orElse("/io.jing");
    String ZK_ADDRESS = ConfigSettings.get("zk.address").orElse("127.0.0.1:2181");
    String THRIFT_SERVER_NAME = ConfigSettings.get("thrift.server.name").orElse("microService");
    String THRIFT_SERVER_VERSION = ConfigSettings.get("thrift.server.version").orElse("1.0");
    String LOG_ROOT_PATH = ConfigSettings.get("log.root.path").orElse("/data/logs");
    String LOG_LEVEL = ConfigSettings.get("log.level").orElse("DEBUG");
    String LOG_REF = ConfigSettings.get("log.ref").orElse("STDOUT");
    String TRACE_ID = "TRACE_ID";
    String INVALID_CODE_PREFIX = "CODE:";
    boolean ALL_IN_ONE = ConfigSettings.get("all.in.one").filter("true"::equalsIgnoreCase).isPresent();


}
