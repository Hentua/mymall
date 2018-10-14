package com.mall.common.utils;

/**
 * @author wank
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultStatus {
    //成功
    SUCCESS(200),
    //失败
    FAIL(400),
    //未认证（签名错误）
    UNAUTHORIZED(401),
    //接口不存在
    NOT_FOUND(404),
    //服务器内部错误
    INTERNAL_SERVER_ERROR(500),
    //无权限
    ACCESS_DENIED(403);


    private final int status;

    ResultStatus(int status) {
        this.status = status;
    }

    public int status() {
        return status;
    }
}
