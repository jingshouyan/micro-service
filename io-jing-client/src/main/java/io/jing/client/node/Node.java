package io.jing.client.node;

import io.jing.base.bean.ServiceInfo;
import io.jing.base.thrift.MicroService;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jingshouyan
 * @date 2018/4/18 0:39
 */
@Data
public class Node {
    private ServiceInfo serviceInfo;
    private AtomicInteger count = new AtomicInteger(0);
    private boolean health = true;
}
