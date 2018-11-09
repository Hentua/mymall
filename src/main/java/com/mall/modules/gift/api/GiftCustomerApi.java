package com.mall.modules.gift.api;

import com.mall.common.service.ServiceException;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.web.BaseController;
import com.mall.modules.gift.entity.GiftConfig;
import com.mall.modules.gift.entity.GiftCustomer;
import com.mall.modules.gift.service.GiftConfigService;
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
 * 会员礼包API
 *
 * @author wankang
 * @date 2018-11-09
 */
@RestController
@RequestMapping(value = "${adminPath}/api")
public class GiftCustomerApi extends BaseController {

    @Autowired
    private GiftCustomerService giftCustomerService;
    @Autowired
    private GiftConfigService giftConfigService;

    /**
     * 获取当前登录用户所有可用礼包列表
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "enabledGiftList", method = RequestMethod.POST)
    public void enabledGiftList(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            GiftCustomer queryCondition = new GiftCustomer();
            queryCondition.setCustomerCode(customerCode);
            queryCondition.setGiftCount(0);
            List<GiftCustomer> giftCustomers = giftCustomerService.findList(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult(giftCustomers));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 获取礼包可兑换列表
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "giftDetailList", method = RequestMethod.POST)
    public void giftDetailList(HttpServletRequest request, HttpServletResponse response) {
        String giftCustomerId = request.getParameter("giftCustomerId");
        try {
            if (StringUtils.isBlank(giftCustomerId)) {
                throw new ServiceException("未选择礼包");
            }
            GiftCustomer giftCustomer = giftCustomerService.get(giftCustomerId);
            if (null == giftCustomer) {
                throw new ServiceException("礼包不存在");
            }
            String giftCategoryId = giftCustomer.getGiftCategory();
            GiftConfig queryCondition = new GiftConfig();
            queryCondition.setGiftCategory(giftCategoryId);
            List<GiftConfig> giftConfigs = giftConfigService.findList(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult(giftConfigs));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 获取礼包详情
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "giftDetail", method = RequestMethod.POST)
    public void giftDetail(HttpServletRequest request, HttpServletResponse response) {
        String giftConfigId = request.getParameter("giftConfigId");
        try {
            if (StringUtils.isBlank(giftConfigId)) {
                throw new ServiceException("未选择礼包");
            }
            GiftConfig giftConfig = giftConfigService.get(giftConfigId);
            renderString(response, ResultGenerator.genSuccessResult(giftConfig));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }
}
