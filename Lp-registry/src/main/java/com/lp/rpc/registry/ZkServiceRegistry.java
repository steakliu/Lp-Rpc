package com.lp.rpc.registry;

import com.lp.rpc.annotation.LpService;
import com.lp.rpc.constants.LpConstant;
import com.lp.rpc.constants.ZNodeType;
import com.lp.rpc.registry.listener.ZkRegistryListener;
import com.lp.rpc.server.LpServer;
import com.lp.rpc.utils.IpUti;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务注册
 */
@AllArgsConstructor
public class ZkServiceRegistry implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ZkServiceRegistry.class);
    /**
     * zk地址
     */
    private final String zkAddress;
    /**
     * 服务名称
     */
    private final String serviceName;

    /**
     * 服务端口
     */
    private final int serverPort;


    /**
     * @param applicationContext
     * @throws BeansException
     */
    @SneakyThrows
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> annotationMap = applicationContext.getBeansWithAnnotation(LpService.class);
        logger.info("服务注册中");
        logger.info("服务注册地址:{}",zkAddress);
        for (Object bean : annotationMap.values()){
            LpService annotation = bean.getClass().getAnnotation(LpService.class);
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            for (Class<? extends Object> inter : interfaces){
                /**
                 * 存在根节点
                 */
                String interfaceName = "/"+inter.toString().replace("interface ","");
                String address = IpUti.getIp();
                String service_name = "/"+serviceName;
                String childService = service_name + interfaceName;
                if (ZkOperation.exists(service_name,true) != null){
                    if (ZkOperation.exists(childService,true) != null){
                        logger.info("服务{}已存在,进行更新服务附加数据", childService);
                        ZkOperation.setData(childService,address.getBytes(),
                                ZkOperation.exists(childService,true).getVersion());
                    }else {
                        logger.info("服务{}不存在,开始注册服务", childService);
                        ZkOperation.create(childService,address.getBytes(),
                                ZNodeType.PERSISTENT);
                    }
                }else {
                    logger.info("持久化根节点：{} 不存在,正在创建",service_name);
                    ZkOperation.create(service_name, LpConstant.ZkHost.getBytes(), ZNodeType.PERSISTENT);
                }
            }
        }
        logger.info("服务注册完成");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<String> list = ZkOperation.getChildren("/"+serviceName, true);
        logger.debug("服务列表：{}",list);
        LpServer lpServer = new LpServer();
        lpServer.startServer(list,serverPort);
        ZkRegistryListener zkRegistryListener = new ZkRegistryListener();
    }
}
