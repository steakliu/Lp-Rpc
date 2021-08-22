package com.lp.rpc.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * TODO
 * 服务地址初始化
 * @author 刘牌
 * @version 1.0
 * @date 2021/8/23 0023 0:05
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LpEnvInitializer implements ApplicationContextAware, InitializingBean {
    /**
     * 远程服务地址
     */
    private String remoteAddress;

    /**
     * 远程服务端口
     */
    private int remotePort;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LpClientProxy.lpEnvInitializer(remoteAddress,remotePort);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
