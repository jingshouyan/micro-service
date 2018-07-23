package io.jing.server.sms.constant;

import com.auth0.jwt.algorithms.Algorithm;
import io.jing.base.util.config.ConfigSettings;
import io.jing.server.constant.ServerConstant;

/**
 * @author jingshouyan
 * #date 2018/6/28 17:42
 */
public interface SmsConstant extends ServerConstant {
    String SMS_KEY = ConfigSettings.get("netease.app.key").orElseThrow(()->new RuntimeException("netease.app.key not set."));
    String SMS_SECRET = ConfigSettings.get("netease.app.secret").orElseThrow(()->new RuntimeException("netease.app.secret not set."));
    String JWT_SECRET = ConfigSettings.get("jwt.secret").orElseThrow(()->new RuntimeException("jwt.secret not set."));
    int CODE_TEMPLATE_ID = ConfigSettings.get("netease.sms.code.templateid").map(Integer::parseInt).orElse(0);
    int CODE_LEN = ConfigSettings.get("netease.sms.code.len").map(Integer::parseInt).orElse(4);
    long CODE_PERIOD =  ConfigSettings.get("netease.sms.code.period.mm").map(Long::valueOf).orElse(10L)*60*1000L;
    String SEND_CODE_URL = "https://api.netease.im/sms/sendcode.action";
    long JWT_PERIOD = ConfigSettings.get("jwt.period.mm").map(Long::valueOf).orElse(10L)*60*1000L;

    int PAY92_PAYID = ConfigSettings.get("pay92.payId").map(Integer::parseInt).orElse(4);
    String PAY92_PAYKEY = ConfigSettings.get("pay92.payKey").orElse("");
    String PAY92_NOTIFY_URL = ConfigSettings.get("pay92.Notify_url").orElse("");
    String PAY92_RETURN_URL = ConfigSettings.get("pay92.Return_url").orElse("");
    String PAY92_PAY_URL = ConfigSettings.get("pay92.pay_url").orElse("");
    int CODE_STATE_OK = 1;
    int CODE_STATE_NO = 2;
}
