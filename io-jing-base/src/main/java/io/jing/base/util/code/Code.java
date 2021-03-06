package io.jing.base.util.code;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jingshouyan
 * @date 2018/4/15 9:36
 */
public class Code {
    public static final int SUCCESS = 1;
    public static final int CLIENT_ERROR = 99700001;
    public static final int SERVICE_NOT_FUND = 99800001;
    public static final int INSTANCE_NOT_FUND = 99800002;
    public static final int UNSUPPORTED_ROUTE_MODE = 99800003;
    public static final int SERVER_ERROR = 99900001;
    public static final int METHOD_NOT_FOUND = 99900002;
    public static final int JSON_PARSE_ERROR = 99900003;
    public static final int PARAM_INVALID = 99900004;
    public static final int USERID_NOTSET = 99900005;
    public static final int TICKET_NOTSET = 99900006;
    public static final int BAD_REQUEST = 99900007;
    public static final int PERMISSION_DENIED = 99900008;
    private static final Map<Integer, String> CODE_MAP = new HashMap<>();

    static {
        CODE_MAP.put(SUCCESS, "success");
        CODE_MAP.put(CLIENT_ERROR, "client error");
        CODE_MAP.put(SERVICE_NOT_FUND, "service not found");
        CODE_MAP.put(INSTANCE_NOT_FUND, "instance not found");
        CODE_MAP.put(UNSUPPORTED_ROUTE_MODE, "unsupported route mode");
        CODE_MAP.put(SERVER_ERROR, "server error");
        CODE_MAP.put(METHOD_NOT_FOUND, "method not found");
        CODE_MAP.put(JSON_PARSE_ERROR, "json parse error");
        CODE_MAP.put(PARAM_INVALID, "param invalid");
        CODE_MAP.put(USERID_NOTSET, "userId not set");
        CODE_MAP.put(TICKET_NOTSET, "ticket not set");
        CODE_MAP.put(BAD_REQUEST,"bad request");
        CODE_MAP.put(PERMISSION_DENIED,"permission denied");
    }

    public static String getMessage(int errCode) {
        String message = CODE_MAP.get(errCode);
        if (null == message) {
            message = "code[" + errCode + "] is undefined";
        }
        return message;
    }

    public static void regCode(int errCode, String message) {
        String msg = CODE_MAP.get(errCode);
        if (null != msg) {
            System.err.println("code :" + errCode + " already in use. old message:[" + msg + "]");
        }
        CODE_MAP.put(errCode, message);
    }
}
