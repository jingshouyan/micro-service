package io.jing.server.iface;

import io.jing.base.thrift.MicroService;

/**
 * @author jingshouyan
 * @date 2018/4/17 8:55
 */
public interface ServiceLoad {
    /**
     * 获取 iface 实例
     * @return
     */
    MicroService.Iface load();
}
