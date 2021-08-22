package com.lp.rpc.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * TODO
 *
 * @author 刘牌
 * @version 1.0
 * @date 2021/8/22 0022 21:55
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {
    private String no;
    private String name;
    private String sex;
}
