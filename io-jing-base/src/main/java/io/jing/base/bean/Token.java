package io.jing.base.bean;

import io.jing.base.thrift.TokenBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author jingshouyan
 * @date 2018/4/14 23:10
 */
@Data@Builder@AllArgsConstructor
public class Token {
    private String userId;
    private String ticket;
    private int clientType;

    public Token(){
    }



    public Token(TokenBean tokenBean){
        userId = tokenBean.getUserId();
        ticket = tokenBean.getTicket();
        clientType = tokenBean.getClientType();
    }

    public TokenBean tokenBean(){
        return new TokenBean()
                .setUserId(userId)
                .setTicket(ticket)
                .setClientType(clientType);
    }

    public Token copy(){
        Token token = new Token();
        token.setUserId(userId);
        token.setTicket(ticket);
        token.setClientType(clientType);
        return token;
    }
}
