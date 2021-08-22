package com.lp.rpc.utils;

import java.util.UUID;

public class UUIDUtil {
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
