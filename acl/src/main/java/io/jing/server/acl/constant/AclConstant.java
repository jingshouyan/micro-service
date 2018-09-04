package io.jing.server.acl.constant;

import io.jing.server.constant.ServerConstant;

/**
 * @author jingshouyan
 * #date 2018/8/30 23:35
 */
public interface AclConstant extends ServerConstant {
    int STATE_ENABLE = 1;
    int STATE_DISABLE = 2;

    int RESOURCE_TYPE_PUB = 1;
    int RESOURCE_TYPE_LOGIN = 2;
    int RESOURCE_TYPE_AUTH = 3;

    long CACHE_SIZE = 1000;
    long CACHE_HOLD_SECOND = 300;
}
