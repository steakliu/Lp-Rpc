package com.lp.rpc.service.impl;

import com.lp.rpc.annotation.LpService;
import com.lp.rpc.service.IUserService;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/8/22 0022 21:29
 */
@LpService
public class UserServiceImpl implements IUserService {
    @Override
    public String sayHello() {
        return "你好";
    }
}
