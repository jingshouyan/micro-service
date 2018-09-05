package io.jing.server.user.netty;

import io.jing.server.user.constant.UserConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.util.Optional;

/**
 * @author jingshouyan
 * @date 2018/4/27 21:09
 */
//@Component
public class ChatServer implements Runnable,Closeable {
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup  = new NioEventLoopGroup();
    private Channel channel;
    private final int port = UserConstant.WS_PORT;

    @Autowired
    private ChatServerInitializer chatServerInitializer;

    @PostConstruct
    public void init(){
        run();
    }

    @Override
    public void run(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(chatServerInitializer)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future = bootstrap.bind(port).syncUninterruptibly();
        channel = future.channel();
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        Optional.ofNullable(channel).map(Channel::close);
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
