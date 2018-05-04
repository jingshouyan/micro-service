package io.jing.ip.test;

import io.jing.base.bean.MonitorInfo;
import io.jing.server.monitor.MonitorUtil;

/**
 * @author jingshouyan
 * @date 2018/4/23 19:45
 */
public class MT {
    public static void main(String[] args) {
        MonitorInfo monitorInfo = MonitorUtil.monitor();
        System.out.println(monitorInfo);
    }
}
