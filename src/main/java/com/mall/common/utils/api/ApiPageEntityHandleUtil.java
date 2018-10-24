package com.mall.common.utils.api;

import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import org.apache.poi.ss.formula.functions.T;

/**
 * 接口分页参数处理
 *
 * @author wankang
 * @since 2018-10-24
 */
public class ApiPageEntityHandleUtil {

    public static Page packagePage(Page page, String pageNo, String pageSize) {
        if(StringUtils.isBlank(pageNo)) {
            pageNo = "0";
        }
        if (StringUtils.isBlank(pageSize)) {
            pageSize = "10";
        }
        page.setPageNo(Integer.valueOf(pageNo));
        page.setPageSize(Integer.valueOf(pageSize));
        return page;
    }
}
