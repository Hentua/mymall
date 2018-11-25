package com.mall.common.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wank
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return  new Result()
                .setData("")
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }
    public static Result genSuccessResult(String message) {
        return  new Result()
                .setData(null == message?"" :message)
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(message);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(null == data?"" :data);
    }

    public static Result genFailResult(String message) {
        return new Result()
               .setData(null == message?"" :message)
                .setStatus(ResultStatus.FAIL)
                .setMessage(message);
    }

    public static Result genFailResult(String message,ResultStatus status ) {
        return new Result()
              .setData(null == message?"" :message)
                .setStatus(status)
                .setMessage(message);
    }
}
