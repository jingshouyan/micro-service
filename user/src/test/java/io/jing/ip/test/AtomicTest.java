package io.jing.ip.test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author jingshouyan
 * @date 2018/4/23 17:47
 */
public class AtomicTest {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();

        System.out.println( atomicInteger.getAndAdd(Integer.MAX_VALUE));
        System.out.println( atomicInteger.getAndAdd(Integer.MAX_VALUE));
        System.out.println( atomicInteger.getAndAdd(Integer.MAX_VALUE));

        LongAdder longAdder = new LongAdder();
        longAdder.add(Long.MAX_VALUE);
        System.out.println(longAdder.longValue());
        longAdder.add(Long.MAX_VALUE);
        System.out.println(longAdder.longValue());
        longAdder.add(Long.MAX_VALUE);
        System.out.println(longAdder.longValue());
    }
}
