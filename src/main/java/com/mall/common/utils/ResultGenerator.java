package com.mall.common.utils;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @author wank
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return  new Result()
                .setData("success")
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }
    public static Result genSuccessResult(String message) {
        return  new Result()
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(message);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setStatus(ResultStatus.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setStatus(ResultStatus.FAIL)
                .setMessage(message);
    }

    public static Result genFailResult(String message,ResultStatus status ) {
        return new Result()
                .setStatus(status)
                .setMessage(message);
    }
}
