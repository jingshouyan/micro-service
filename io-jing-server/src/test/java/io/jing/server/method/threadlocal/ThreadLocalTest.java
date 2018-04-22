package io.jing.server.method.threadlocal;

import io.jing.base.bean.Token;
import io.jing.base.util.id.IdGen;

/**
 * @author jingshouyan
 * @date 2018/4/15 18:20
 */
public class ThreadLocalTest {

    public static final ThreadLocal<Token> TOKEN_THREAD_LOCAL = ThreadLocal.withInitial(Token::new);

    public static void main(String[] args) {


        Token token = Token.builder().userId("abc").ticket("123").build();
        TOKEN_THREAD_LOCAL.set(token);
        Runnable runnable = ()->{
//            TOKEN_THREAD_LOCAL.set(token);
            Token token1 = TOKEN_THREAD_LOCAL.get();
            token1.setUserId("fffff");
            System.out.println("thread:"+token1);
        };
        new Thread(runnable).start();

        Token token1 = TOKEN_THREAD_LOCAL.get();
//        TOKEN_THREAD_LOCAL.remove();
//        Token token2 = TOKEN_THREAD_LOCAL.load();
        System.out.println(token);
        System.out.println(token1);
//        System.out.println(token2);

    }

}
