package com.lp.rpc.client;

import com.lp.rpc.codec.LpKryoDecoder;
import com.lp.rpc.codec.LpKryoEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

public class LpChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        ChannelPipeline pipeline = nioSocketChannel.pipeline();
        pipeline.addLast("encoder", new LpKryoEncoder())
                .addLast("decoder",new LpKryoDecoder())
                .addLast("handler",new LpClientHandler());
    }
}
