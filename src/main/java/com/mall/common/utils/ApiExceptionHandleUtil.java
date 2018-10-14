package com.mall.common.utils;

import com.mall.common.service.ServiceException;

/**
 * 服务错误处理
 *
 * @author wankang
 * @since 2018-10-11
 */
public class ApiExceptionHandleUtil {

    public static Result normalExceptionHandle(Exception e) {
        if(StringUtils.isNotBlank(e.getMessage()) && e instanceof ServiceException) {
            return ResultGenerator.genFailResult(e.getMessage());
        }else {
            e.printStackTrace();
            return ResultGenerator.genFailResult("服务内部错误", ResultStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
