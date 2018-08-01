package io.jing.server.stream;

import com.google.common.collect.Lists;

import java.util.List;

public class StreamTest {

    public static void main(String[] args) {
        List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
        System.out.println("sum is:"+nums.stream().
                filter(num -> num != null).
                distinct().mapToInt(num -> num * 2).
                skip(2).
                limit(4).
                peek(System.out::println).
                sum());
    }
}
