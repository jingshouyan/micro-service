package io.jing.ip.test;

import java.util.stream.IntStream;

/**
 * @author jingshouyan
 * @date 2018/4/24 20:19
 */
public class IntStreamTest {
    public static void main(String[] args) {
        IntStream.range(1,10).forEach(System.out::println);
    }
}
