package io.jing.ip.test;

/**
 * @author jingshouyan
 * #date 2018/5/26 17:01
 */
public class IntTest {
    public static void main(String[] args) {
        for (int i = 0; i < 40; i++) {
            int a = Integer.numberOfLeadingZeros(i);
            String b = Integer.toBinaryString(i);
            System.out.println("i:"+i+"=====>"+a+"===>"+b+"====>");
        }
    }


}
