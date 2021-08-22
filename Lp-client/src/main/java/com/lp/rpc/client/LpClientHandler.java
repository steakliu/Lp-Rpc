package com.lp.rpc.client;

import com.lp.rpc.domain.result.LpCode;
import com.lp.rpc.domain.result.LpResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LpClientHandler extends ChannelInboundHandlerAdapter {

    private Object response;

    public Object getResponse() {
        return response;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LpResult lpResult = (LpResult)msg;
        if (lpResult.getCode() == LpCode.SUCCESS){
            response = lpResult.getData();
        }
        else {
            response = LpResult.fail(msg);
        }
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }
}
