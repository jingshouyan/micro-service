package io.jing.server.message.util;


import org.apache.commons.lang.StringUtils;

/**
 * @author jingshouyan
 * #date 2018/6/5 21:00
 */
public class MessageUtil {

    private static final int LONG_MAX_POSITION = 63;

    public static String messageBeanId(String userId,long messageId){
        return userId+"@"+Long.toHexString(messageId);
    }

    public static boolean yon(long flag,int position){
        if(position>LONG_MAX_POSITION||position<0){
            return false;
        }
        return (flag>>position & 1L) == 1L;
    }

    public static long setBit(long flag,int position,boolean yon){
        if(position>LONG_MAX_POSITION||position<0){
            return flag;
        }
        if(yon){
            return 1L<<position | flag;
        }else {
            return ~(1L<<position) & flag;
        }
    }

    public static void main(String[] args) {
        long a = 1453445345;
        p(a);
        for (int i = 0; i < 64; i++) {
            long c ;
            c = setBit(a,i,false);
            p(c);
            c = setBit(a,i,true);
            p(c);
        }
    }

    private static void p(long a){
        String b = Long.toBinaryString(a);
        b = StringUtils.leftPad(b,64,'0');
//        b = String.format("%64s",b);
        System.out.println(b);
    }
}
