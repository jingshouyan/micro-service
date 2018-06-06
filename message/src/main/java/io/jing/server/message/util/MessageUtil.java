package io.jing.server.message.util;

/**
 * @author jingshouyan
 * #date 2018/6/5 21:00
 */
public class MessageUtil {

    public static String messageBeanId(String userId,long messageId){
        return userId+"@"+Long.toHexString(messageId);
    }

}
