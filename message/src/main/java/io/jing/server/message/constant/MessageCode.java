package io.jing.server.message.constant;

import io.jing.base.util.code.Code;

public class MessageCode {

    public static final int UNSUPPORTED_TARGET_TYPE = 30001;

    static {
        Code.regCode(UNSUPPORTED_TARGET_TYPE, "unsupported message target type");
    }
}
