package com.lp.rpc.service.impl;

import com.lp.rpc.annotation.LpService;
import com.lp.rpc.bean.User;
import com.lp.rpc.service.UserService;
/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/8/22 0022 21:29
 */
@LpService
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        return user;
    }
}
