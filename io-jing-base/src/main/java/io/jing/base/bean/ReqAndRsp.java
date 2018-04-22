package io.jing.base.bean;

import lombok.Data;

/**
 * @author jingshouyan
 * @date 2018/4/15 15:38
 */
@Data
public class ReqAndRsp {
    private Token token;
    private Req req;
    private Rsp rsp;
    private long startAt;
    private long cost;
    private String traceId;
}
