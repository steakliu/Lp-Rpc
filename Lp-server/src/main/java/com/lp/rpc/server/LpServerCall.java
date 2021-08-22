package com.lp.rpc.server;

import com.lp.rpc.domain.LpRequest;
import com.lp.rpc.utils.LpStringUtil;
import io.netty.util.internal.StringUtil;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class LpServerCall {

    public static Object serviceCall(LpRequest lpRequest, List<String> serviceList) throws ClassNotFoundException {

        if (serviceList.size() < 1){
            System.out.println("无服务");
        }
        int lastDot = LpStringUtil.getLastIndex(lpRequest.getClassName(),'.') + 1;
        //客户端传过来的接口名
        String clientInterfaceName = lpRequest.getClassName().substring(lastDot);
        String targetInterfaceName = "";
        String interfacePath = "";
        for (String s : serviceList) {
            int registryInterfaceIndex = s.lastIndexOf(".") + 1;
            String registryInterfaceName = s.substring(registryInterfaceIndex);
            if (registryInterfaceName.equals(clientInterfaceName)) {
                targetInterfaceName =s;
                interfacePath = s.replace(s.substring(registryInterfaceIndex -1),"");
                break;
            }
        }
        /**
         * todo 此处如果有多个实现类，有多个同名服务还未解决
         */
        Object result = null;
        try {
            Class<?> superClass = Class.forName(targetInterfaceName);
            Reflections reflections = new Reflections(interfacePath);
            Set<Class<?>> classes = reflections.getSubTypesOf((Class<Object>) superClass);
            Object instance = Class.forName((classes.toArray(new Class[0]))[0].getName()).newInstance();
            Method method = instance.getClass().getMethod(lpRequest.getMethodName(), lpRequest.getParamTypes());
            result = method.invoke(instance, lpRequest.getParams());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
