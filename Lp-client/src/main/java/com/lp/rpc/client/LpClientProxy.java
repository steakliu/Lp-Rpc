package com.lp.rpc.client;

import com.lp.rpc.client.builder.MessageBuilder;
import com.lp.rpc.codec.LpKryoDecoder;
import com.lp.rpc.codec.LpKryoEncoder;
import com.lp.rpc.domain.LpMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LpClientProxy{

    private static String remoteAddress;

    private static int remotePort;

    public static void lpEnvInitializer(String remoteAddress,int remotePort){
        LpClientProxy.remoteAddress = remoteAddress;
        LpClientProxy.remotePort = remotePort;
    }

    public static  <T> T createProxy(final Class<?> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                LpMessage message = MessageBuilder.build(clazz, method, args);
                NioEventLoopGroup group = new NioEventLoopGroup();
                LpClientHandler lpClientHandler = new LpClientHandler();
                try {
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(group)
                            .channel(NioSocketChannel.class)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    ChannelPipeline pipeline = socketChannel.pipeline();
                                    pipeline.addLast("encoder",new LpKryoEncoder())
                                            .addLast("decoder",new LpKryoDecoder())
                                            .addLast("handler", lpClientHandler);
                                }
                            });
                    ChannelFuture future = bootstrap.connect(remoteAddress, remotePort).sync();
                    future.channel().writeAndFlush(message).sync();
                    future.channel().closeFuture().sync();
                }finally {
                    group.shutdownGracefully();
                }
                return lpClientHandler.getResponse();
            }
        });
    }
}
