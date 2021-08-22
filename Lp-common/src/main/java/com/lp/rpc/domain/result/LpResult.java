package com.lp.rpc.domain.result;

import lombok.*;
import lombok.experimental.Accessors;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class LpResult {
    public int code;
    public String msg;
    public Object data;

    public static LpResult success(Object data){
        return new LpResult().setCode(LpCode.SUCCESS)
                .setMsg(LpMsg.SUCCESS)
                .setData(data);
    }

    public static LpResult fail(Object data){
        return new LpResult().setCode(LpCode.FAIL)
                .setMsg(LpMsg.FAIL)
                .setData(data);
    }

    public static LpResult customFail(int code, String msg , Object data){
        return new LpResult(code,msg,data);
    }
}
