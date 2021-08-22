package com.lp.rpc.constants;

/**
 * zookeeper持久化节点类型
 */
public interface ZNodeType {
    /**
     * 持久化节点
     */
    public static final int PERSISTENT = 1;

    /**
     * 持久化顺序节点
     */
    public static final int PERSISTENT_SEQUENTIAL = 2;

    /**
     * 临时节点
     */
    public static final int EPHEMERAL = 3;

    /**
     * 临时顺序节点
     */
    public static final int EPHEMERAL_SEQUENTIAL = 4;

}
