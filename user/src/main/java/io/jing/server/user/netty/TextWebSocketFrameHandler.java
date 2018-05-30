package io.jing.server.user.netty;

import io.jing.base.bean.Token;
import io.jing.base.util.json.JsonUtil;
import io.jing.server.user.constant.UserConstant;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * @date 2018/5/3 15:22
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>  {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            ctx.pipeline().remove(HttpRequestHandler.class);
            Token token = ctx.channel().attr(UserConstant.TOKEN_KEY).get();
            ctx.writeAndFlush(message(token));
        }else{
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        ctx.writeAndFlush(msg.retain());
    }

    private TextWebSocketFrame message(Object obj){
        return new TextWebSocketFrame(JsonUtil.toJsonString(obj));
    }
}
