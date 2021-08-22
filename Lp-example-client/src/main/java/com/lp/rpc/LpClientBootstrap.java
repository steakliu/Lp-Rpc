package com.lp.rpc;

import com.lp.rpc.bean.User;
import com.lp.rpc.client.LpClientProxy;
import com.lp.rpc.service.HelloService;
import com.lp.rpc.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/8/22 0022 21:31
 */
public class LpClientBootstrap {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        HelloService proxy = LpClientProxy.createProxy(HelloService.class);
        System.out.println(proxy.sayHello());
        UserService userService = LpClientProxy.createProxy(UserService.class);
        User user = userService.getUser(new User().setNo("1111").setName("刘牌").setSex("男"));
        System.out.println(user);
    }
}

