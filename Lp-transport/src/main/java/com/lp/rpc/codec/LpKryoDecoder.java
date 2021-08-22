package com.lp.rpc.codec;

import com.lp.rpc.constants.LpConstant;
import com.lp.rpc.domain.LpRequest;
import com.lp.rpc.domain.result.LpResult;
import com.lp.rpc.kryo.LpKryoSerializerFactory;
import com.lp.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class LpKryoDecoder extends ByteToMessageDecoder {
    private final Serializer serializer = LpKryoSerializerFactory.getSerializer();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte b = byteBuf.readByte();
        byteBuf.markReaderIndex();
        int length = byteBuf.readInt();
        if (length <= 0){
            channelHandlerContext.close();
        }
        if (byteBuf.readableBytes() < length){
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        if (LpConstant.LpRpcMsgRequest == b){
            LpRequest lpRequest = serializer.deserialize(bytes, LpRequest.class);
            list.add(lpRequest);
        }else {
            LpResult lpResult = serializer.deserialize(bytes, LpResult.class);
            list.add(lpResult);
        }
    }
}
