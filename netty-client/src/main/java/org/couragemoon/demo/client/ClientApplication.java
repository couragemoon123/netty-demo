package org.couragemoon.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.couragemoon.demo.client.config.EnvProperties;

import java.util.Date;

public class ClientApplication {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println(msg);
                            }
                        });
                    }
                });

        try {
            String ip = EnvProperties.getServerIp();
            int port = EnvProperties.getServerPort();
            Channel channel = bootstrap.connect(ip, port).sync().channel();
            for (int i = 0; i < 10; i++) {
                channel.writeAndFlush(new Date() + " hello world!");
                Thread.sleep(1000);
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
