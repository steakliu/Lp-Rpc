package com.lp.rpc.server;

import com.lp.rpc.constants.LpConstant;
import com.lp.rpc.domain.LpMessage;
import com.lp.rpc.domain.LpRequest;
import com.lp.rpc.domain.result.LpResult;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LpServerHandler extends ChannelInboundHandlerAdapter {

    private  List<String> serviceNameList = new ArrayList<>();

    public LpServerHandler(List<String> list) {
        this.serviceNameList = list;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LpRequest lpRequest = (LpRequest) msg;
        Object result = LpServiceCall.serviceCall(lpRequest, serviceNameList);
        LpResult lpResult = LpResult.success(result);
        LpMessage lpMessage = new LpMessage();
        lpMessage.setMessageType(LpConstant.GalsangRpcMsgResponse);
        lpMessage.setData(lpResult);
        ctx.writeAndFlush(lpMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
