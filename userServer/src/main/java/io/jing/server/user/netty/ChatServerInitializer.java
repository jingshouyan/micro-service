package io.jing.server.user.netty;

import io.jing.server.user.constant.UserConstant;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jingshouyan
 * @date 2018/4/27 21:37
 */
@Component
public class ChatServerInitializer extends ChannelInitializer<Channel> {
    public static final int MAX_CONTENT_LENGTH = 5*1024*1024;

    @Autowired
    private HttpRequestHandler httpRequestHandler;

    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;
    @Override
    protected void initChannel(Channel channel) {
        ChannelPipeline p = channel.pipeline();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        HttpContentCompressor httpContentCompressor = new HttpContentCompressor();
        HttpServerCodec httpServerCodec = new HttpServerCodec();
        HttpObjectAggregator httpObjectAggregator = new HttpObjectAggregator(MAX_CONTENT_LENGTH);
        WebSocketServerProtocolHandler webSocketServerProtocolHandler = new WebSocketServerProtocolHandler(UserConstant.WS_URI);
        p.addLast("logger",loggingHandler)
                .addLast("httpCodec",httpServerCodec)
                .addLast("httpObject",httpObjectAggregator)
                .addLast("httpGzip",httpContentCompressor)
                .addLast("httpHandler",httpRequestHandler)
                .addLast("webSocket",webSocketServerProtocolHandler)
                .addLast("messageHandler",textWebSocketFrameHandler)
        ;
    }
}
