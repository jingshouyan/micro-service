package io.jing.server.sms.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jing.server.sms.bean.UserBean;
import io.jing.server.sms.constant.SmsConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author jingshouyan
 * #date 2018/7/4 16:56
 */
@Slf4j
public class JwtUtil {

    private static Algorithm algorithm;
    static {
        try{
            algorithm = Algorithm.HMAC256(SmsConstant.JWT_SECRET);
        }catch (Exception e){
            log.error("JWT algorithm error",e);
        }

    }

    public static String token(UserBean userBean){
        Date exp = new Date(System.currentTimeMillis()+SmsConstant.JWT_PERIOD);
        String token = JWT.create()
                .withClaim("nickname",userBean.getNickname())
                .withClaim("id",userBean.getId())
                .withExpiresAt(exp)
                .sign(algorithm);
        return token;
    }
}
