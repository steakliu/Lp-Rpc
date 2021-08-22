package com.lp.rpc.utils;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/8/23 0023 0:54
 */
public class LpStringUtil {

    /**
     * 得到指点符号在字符串的索引
     * @param str
     * @return
     */
    public static int getLastIndex(String str,char c){
        return str.lastIndexOf(c);
    }
}
