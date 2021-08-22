package com.lp.rpc.kryo;


import com.lp.rpc.serializer.Serializer;

public class LpKryoSerializerFactory {
    public static Serializer getSerializer(){
        return new LpKryoSerializer();
    }
}
