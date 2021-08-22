package com.lp.rpc.client;

import com.lp.rpc.domain.result.LpCode;
import com.lp.rpc.domain.result.LpResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LpClientHandler extends ChannelInboundHandlerAdapter {

    private Object galsangResponse;

    public Object getGalsangResponse() {
        return galsangResponse;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LpResult lpResult = (LpResult)msg;
        if (lpResult.getCode() == LpCode.SUCCESS){
            galsangResponse = lpResult.getData();
        }
        else {
            galsangResponse = LpResult.fail(msg);
        }
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
