package com.lp.rpc.bootstrap;

import com.lp.rpc.client.LpClientProxy;
import com.lp.rpc.service.HelloService;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/8/22 0022 21:31
 */
public class LpClientBootstrap {
    public static void main(String[] args) {
        HelloService proxy = LpClientProxy.createProxy(HelloService.class);
        System.out.println(proxy.sayHello());
    }
}
