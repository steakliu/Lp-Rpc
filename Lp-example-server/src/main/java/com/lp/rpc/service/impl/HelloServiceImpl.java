package com.lp.rpc.service.impl;

import com.lp.rpc.annotation.LpService;
import com.lp.rpc.service.HelloService;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/8/22 0022 19:29
 */
@LpService
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return "hello lp";
    }
}
