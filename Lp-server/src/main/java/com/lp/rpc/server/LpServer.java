package com.lp.rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.List;

public class LpServer {

    public void startServer(List<String> list,int serverPort){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .localAddress(serverPort)
                    .childHandler(new LpServerChannelInitializer(list));
            ChannelFuture future = serverBootstrap.bind(serverPort).sync();
            future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
