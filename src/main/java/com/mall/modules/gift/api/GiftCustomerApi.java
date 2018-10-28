package com.mall.modules.gift.api;

import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.web.BaseController;
import com.mall.modules.gift.entity.GiftCustomer;
import com.mall.modules.gift.service.GiftCustomerService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户礼包接口
 *
 * @author wankang
 * @since 2018-10-28
 */
@RestController
@RequestMapping(value = "${adminPath}/api")
public class GiftCustomerApi extends BaseController {

    @Autowired
    private GiftCustomerService giftCustomerService;

    /**
     * 获取当前登录用户所有可用礼包列表
     *
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "enabledGiftList", method = RequestMethod.POST)
    public void enabledGiftList(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            GiftCustomer queryCondition = new GiftCustomer();
            queryCondition.setCustomerCode(customerCode);
            List<GiftCustomer> giftCustomerList = giftCustomerService.findList(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult(giftCustomerList));
        }catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }
}
