package com.lp.rpc.codec;

import com.lp.rpc.constants.LpConstant;
import com.lp.rpc.domain.LpMessage;
import com.lp.rpc.domain.LpRequest;
import com.lp.rpc.domain.result.LpResult;
import com.lp.rpc.kryo.LpKryoSerializerFactory;
import com.lp.rpc.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class LpKryoEncoder extends MessageToByteEncoder<LpMessage> {

    private final Serializer serializer = LpKryoSerializerFactory.getSerializer();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, LpMessage o, ByteBuf byteBuf) throws Exception {
        byte[] bytesBody;
        if (o.getMessageType() == LpConstant.GalsangRpcMsgRequest){
            LpRequest lpRequest = (LpRequest)o.getData();
            bytesBody = serializer.serialize(lpRequest);
            byteBuf.writeByte(LpConstant.GalsangRpcMsgRequest);
        }
        else {
            LpResult lpResult =  (LpResult)o.getData();
            bytesBody = serializer.serialize(lpResult);
            byteBuf.writeByte(LpConstant.GalsangRpcMsgResponse);
        }
        int length = bytesBody.length ;
        byteBuf.writeInt(length);
        byteBuf.writeBytes(bytesBody);
    }
}
