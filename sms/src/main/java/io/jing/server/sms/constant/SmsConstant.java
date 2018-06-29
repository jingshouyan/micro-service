package io.jing.server.sms.constant;

import io.jing.base.util.config.ConfigSettings;
import io.jing.server.constant.ServerConstant;

/**
 * @author jingshouyan
 * #date 2018/6/28 17:42
 */
public interface SmsConstant extends ServerConstant {
    String SMS_KEY = ConfigSettings.get("netease.app.key").orElseThrow(()->new RuntimeException("netease.app.key not set."));
    String SMS_SECRET = ConfigSettings.get("netease.app.secret").orElseThrow(()->new RuntimeException("netease.app.secret not set."));
    int CODE_TEMPLATE_ID = ConfigSettings.get("netease.sms.code.templateid").map(Integer::parseInt).orElse(0);
    int CODE_LEN = ConfigSettings.get("netease.sms.code.len").map(Integer::parseInt).orElse(4);
    long CODE_PERIOD =  ConfigSettings.get("netease.sms.code.period.mm").map(Long::valueOf).orElse(10L)*60*1000L;
    String SEND_CODE_URL = "https://api.netease.im/sms/sendcode.action";

    int CODE_STATE_OK = 1;
    int CODE_STATE_NO = 2;
}
