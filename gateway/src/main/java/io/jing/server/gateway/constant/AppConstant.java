package io.jing.server.gateway.constant;


import io.jing.base.util.config.ConfigSettings;
import io.jing.server.constant.ServerConstant;

/** * @author jingshouyan
 * #date 2018/7/6 21:33
 */
public interface AppConstant extends ServerConstant {
    long TOKEN_CACHE_SIZE = ConfigSettings.get("token.cache.size").map(Long::valueOf).orElse(10000L);
    long TOKEN_CACHE_DURATION_SECOND = ConfigSettings.get("token.cache.duration.second").map(Long::valueOf).orElse(120L);
}
