package com.mall.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

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
        Result result = new Result();
        result.setStatus(ResultStatus.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        if(null == data){
            result.setData("");
        }

        if(data instanceof List){
            List l = (List) data;
            if(l.size() == 0){
                result.setData("");
            }
        }
        return result;
    }

    public static Result genFailResult(String message) {
        return new Result()
//               .setData(null == message?"" :message)
                .setData("")
                .setStatus(ResultStatus.FAIL)
                .setMessage(message);
    }

    public static Result genFailResult(String message,ResultStatus status) {
        return new Result()
//              .setData(null == message?"" :message)
                .setData("")
                .setStatus(status)
                .setMessage(message);
    }
}
