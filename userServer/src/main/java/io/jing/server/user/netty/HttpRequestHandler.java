package io.jing.server.user.netty;

import io.jing.base.bean.Token;
import io.jing.server.user.constant.UserConstant;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author jingshouyan
 * @date 2018/4/27 22:12
 */
@ChannelHandler.Sharable
@Component
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> implements UserConstant {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        String uri = StringUtils.substringBefore(request.uri(),"?");
        if(WS_URI.equalsIgnoreCase(uri)){
            QueryStringDecoder query = new QueryStringDecoder(request.uri());
            Map<String, List<String>> map = query.parameters();
            List<String> tickets = map.get(Ticket);
            if(null != tickets && !tickets.isEmpty()){
                String ticket = tickets.get(0);
                ctx.channel().attr(TOKEN_KEY).set(Token.builder().ticket(ticket).build());
                request.setUri(uri);
                ctx.fireChannelRead(request.retain());
            }
        }else{
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            boolean keepAlive = HttpUtil.isKeepAlive(request);
            if(keepAlive) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
        }

    }
}
