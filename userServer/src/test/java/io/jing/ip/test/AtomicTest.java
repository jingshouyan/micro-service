package io.jing.ip.test;

import java.util.concurrent.atomic.AtomicInteger;

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
    }
}
