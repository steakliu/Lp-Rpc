package com.lp.rpc.client.builder;

import com.lp.rpc.constants.LpConstant;
import com.lp.rpc.domain.LpMessage;
import com.lp.rpc.domain.LpRequest;
import com.lp.rpc.utils.UUIDUtil;

import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/8/23 0023 0:29
 */
public class MessageBuilder {

    public static LpMessage build(Class<?> clazz , Method method , Object[] args){
        LpRequest lpRequest = new LpRequest();
        lpRequest.setRequestId(UUIDUtil.generateUUID());
        lpRequest.setClassName(clazz.getName());
        lpRequest.setMethodName(method.getName());
        lpRequest.setParams(args);
        lpRequest.setParamTypes(method.getParameterTypes());
        LpMessage lpMessage = new LpMessage();
        lpMessage.setMessageType(LpConstant.LpRpcMsgRequest);
        lpMessage.setData(lpRequest);
        return lpMessage;
    }
}
