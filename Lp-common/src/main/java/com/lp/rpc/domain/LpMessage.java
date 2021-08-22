package com.lp.rpc.domain;

import java.io.Serializable;


public class LpMessage implements Serializable {
    private byte messageType;
    private String version;
    private Object data;

    public byte getMessageType() {
        return messageType;
    }

    public void setMessageType(byte messageType) {
        this.messageType = messageType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GalsangMessage{" +
                "messageType=" + messageType +
                ", version='" + version + '\'' +
                ", data=" + data +
                '}';
    }
}
