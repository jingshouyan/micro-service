package io.jing.ip.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import io.jing.base.bean.MonitorInfo;
import io.jing.server.monitor.MonitorUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
