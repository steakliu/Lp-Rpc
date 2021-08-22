package com.lp.rpc.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.lp.rpc.serializer.Serializer;
import io.netty.util.concurrent.FastThreadLocal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class LpKryoSerializer implements Serializer {

    final FastThreadLocal<Kryo> kryoThreadLocal = new FastThreadLocal<Kryo>(){
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.setReferences(true);
            return kryo;
        }
    };

    public Kryo getCurrentKryoInstance(){
        return kryoThreadLocal.get();
    }


    @Override
    public byte[] serialize(Object object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream);
            Kryo currentKryoInstance = getCurrentKryoInstance();
            currentKryoInstance.writeObject(output,object);
            output.flush();
            output.close();
            kryoThreadLocal.remove();
            return byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            throw new RuntimeException("序列化异常");
        }
    }

    /**
     *
     * @param bytes
     * @param <T>
     * @return
     */
    @Override
    public <T> T deserialize(byte[] bytes,Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(byteArrayInputStream);
        Kryo currentKryoInstance = getCurrentKryoInstance();
        T o = null;
        try {
            o = currentKryoInstance.readObjectOrNull(input, clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        input.close();
        kryoThreadLocal.remove();
        return (T) clazz.cast(o);
    }
}
