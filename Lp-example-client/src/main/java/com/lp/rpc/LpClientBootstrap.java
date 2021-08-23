package com.lp.rpc;

import com.lp.rpc.bean.User;
import com.lp.rpc.client.LpClientProxy;
import com.lp.rpc.service.HelloService;
import com.lp.rpc.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
        long s = System.currentTimeMillis();
                for (int i = 0 ; i < 1000 ; i++){
                    HelloService proxy = LpClientProxy.createProxy(HelloService.class);
                    System.out.println(proxy.sayHello());
                }
                long a = System.currentTimeMillis() - s;
        System.out.println("total  "+a);
//            }
//        });


//        UserService userService = LpClientProxy.createProxy(UserService.class);
//        User user = userService.getUser(new User().setNo("1111").setName("刘牌").setSex("男"));
//        System.out.println(user);
    }
}

