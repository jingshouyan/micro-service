package io.jing.server.gateway.helper;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import io.jing.base.bean.Req;
import io.jing.base.bean.Rsp;
import io.jing.base.bean.Token;
import io.jing.base.exception.MicroServiceException;
import io.jing.base.util.code.Code;
import io.jing.client.util.ClientUtil;
import io.jing.server.gateway.constant.AppConstant;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author jingshouyan
 * #date 2018/7/6 21:07
 */
@Component
public class TokenHelper implements AppConstant {

    private static final LoadingCache<String,Token> TOKEN_LOADING_CACHE = Caffeine.newBuilder()
            .maximumSize(TOKEN_CACHE_SIZE)
            .expireAfterAccess(TOKEN_CACHE_DURATION_SECOND,TimeUnit.SECONDS)
            .build(ticket->{
                Token token = new Token();
                token.setTicket(ticket);
                Req req = Req.builder().service("user").method("getToken").param("{}").build();
                Rsp rsp = ClientUtil.call(token,req);
                if(rsp.getCode()!=Code.SUCCESS){
                    throw new MicroServiceException(rsp.getCode(),"user:"+rsp.getMessage());
                }
                token = rsp.get(Token.class);
                return token;
            });

    public Token getToken(String ticket){
        return TOKEN_LOADING_CACHE.get(ticket);
    }

    public void removeToken(Token token){
        TOKEN_LOADING_CACHE.invalidate(token.getTicket());
        if(token.valid()){
            Req req = Req.builder().service("user").method("Logout").param("{}").build();
            ClientUtil.call(token,req);
        }
    }

}
