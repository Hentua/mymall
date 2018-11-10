package com.mall.common.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * @author wank
 * 统一API响应结果封装
 */
public class Result {
    private int status;
    private String message;
    private Object data;
    private Long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public Result setStatus(ResultStatus resultStatus) {
        this.status = resultStatus.status();
        return this;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        if (null == this.data) {
            this.data = new Object();
        }
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, filter);
    }

    private ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null) {
                return "";
            }
            return v;
        }
    };


}
