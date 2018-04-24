package io.jing.client.constant;

import io.jing.base.constant.BaseConstant;
import io.jing.base.util.config.ConfigSettings;

/**
 * @author jingshouyan
 * @date 2018/4/17 21:36
 */
public interface ClientConstant extends BaseConstant {
    int TRANSPORT_POOL_MIN_IDLE = ConfigSettings.get("transport.pool.min.idle").map(Integer::parseInt).orElse(10);
    int TRANSPORT_POOL_MAX_IDLE = ConfigSettings.get("transport.pool.max.idle").map(Integer::parseInt).orElse(40);
    int TRANSPORT_POOL_MAX_TOTAL = ConfigSettings.get("transport.pool.max.total").map(Integer::parseInt).orElse(240);
}
