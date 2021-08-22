package com.lp.rpc.constants;

public interface LpConstant {

    public final String serviceName = "/galsang";

    public final String ZkHost = "116.198.160.39";

    public final int zkTimeOut = 6000;
    /**
     * 消息为请求request
     */
    public final byte GalsangRpcMsgRequest = 1;

    /**
     * 消息为相应response
     */
    public final byte GalsangRpcMsgResponse = 2;

    /**
     * 服务端监听端口
     */
    public final int CalsangPort = 999;
}
