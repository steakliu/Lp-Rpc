package com.lp.rpc.server;


import com.lp.rpc.codec.LpKryoDecoder;
import com.lp.rpc.codec.LpKryoEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/8/23 0023 0:39
 */
public class LpServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 服务集合
     */
    private List<String> list = new ArrayList<>();

    public LpServerChannelInitializer(List<String> list) {
        this.list = list;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("encoder",new LpKryoEncoder())
                .addLast("decoder",new LpKryoDecoder())
                .addLast(new LpServerHandler(list));
    }
}
