package com.lp.rpc.client;

import com.lp.rpc.codec.LpKryoDecoder;
import com.lp.rpc.codec.LpKryoEncoder;
import com.lp.rpc.constants.LpConstant;
import com.lp.rpc.domain.LpMessage;
import com.lp.rpc.domain.LpRequest;
import com.lp.rpc.utils.UUIDUtil;
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

public class LpClientProxy {

    public static <T> T createProxy(final Class<?> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                LpRequest lpRequest = new LpRequest();
                lpRequest.setRequestId(UUIDUtil.generateUUID());
                lpRequest.setClassName(clazz.getName());
                lpRequest.setMethodName(method.getName());
                lpRequest.setParams(args);
                lpRequest.setParamTypes(method.getParameterTypes());
                LpMessage lpMessage = new LpMessage();
                lpMessage.setMessageType(LpConstant.GalsangRpcMsgRequest);
                lpMessage.setData(lpRequest);
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
                    ChannelFuture future = bootstrap.connect("127.0.0.1", LpConstant.CalsangPort).sync();
                    future.channel().writeAndFlush(lpMessage).sync();
                    future.channel().closeFuture().sync();
                }finally {
                    group.shutdownGracefully();
                }
                return lpClientHandler.getGalsangResponse();
            }
        });
    }
}
