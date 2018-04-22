package io.jing.base.bean;

import io.jing.base.thrift.TokenBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jingshouyan
 * @date 2018/4/14 23:10
 */
@Data@Builder@AllArgsConstructor
public class Token {
    private String userId;
    private String ticket;

    public Token(){
    }



    public Token(TokenBean tokenBean){
        userId = tokenBean.getUserId();
        ticket = tokenBean.getTicket();
    }

    public TokenBean tokenBean(){
        return new TokenBean()
                .setUserId(userId)
                .setTicket(ticket);
    }

    public Token copy(){
        Token token = new Token();
        token.setUserId(userId);
        token.setTicket(ticket);
        return token;
    }
}
