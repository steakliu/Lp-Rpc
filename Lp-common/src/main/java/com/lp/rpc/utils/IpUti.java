package com.lp.rpc.utils;

import com.lp.rpc.exception.UtilException;

import java.net.InetAddress;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/8/22 0022 21:04
 */
public class IpUti {

    /**
     * 获取本机IP
     * @return
     */
    public static String getIp(){
        String address;
        try {
             address = InetAddress.getLocalHost().getHostAddress();
        }catch (Exception e){
            throw new UtilException("获取地址异常");
        }
        return address;
    }
}
