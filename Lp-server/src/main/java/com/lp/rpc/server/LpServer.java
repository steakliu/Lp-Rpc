package com.lp.rpc.server;

import com.lp.rpc.codec.LpKryoDecoder;
import com.lp.rpc.codec.LpKryoEncoder;
import com.lp.rpc.constants.LpConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.List;

public class LpServer {

    public void startServer(List<String> list){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .localAddress(LpConstant.CalsangPort)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("encoder",new LpKryoEncoder())
                                    .addLast("decoder",new LpKryoDecoder())
                                    .addLast(new LpServerHandler(list));
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(LpConstant.CalsangPort).sync();
            future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
