package io.jing.server.hashcode;

/**
 * @author jingshouyan
 * #date 2018/5/31 12:48
 */
public class HashcodeTest {
    public static void main(String[] args) {
        String s = "4WQy8pUDhrtZncXp3qdDnbMVtDUmhOsZi7FADh/PXBk= ";
        System.out.println(s.hashCode());
        System.out.println(s.trim().hashCode());
    }
}
