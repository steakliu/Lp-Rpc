package com.lp.rpc.domain;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
@Setter
@Getter
public class LpResponse implements Serializable {
    private Integer code;
    private String message;
    private Object data;
}
