package com.mall.modules.account.api;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.DateUtils;
import com.mall.common.utils.Result;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.account.entity.AccountInfo;
import com.mall.modules.account.service.AccountInfoService;
import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.commission.service.CommissionInfoService;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.order.entity.OrderInfo;
import com.mall.modules.order.service.OrderInfoService;
import com.mall.modules.settlement.service.SettlementInfoService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 账户明细Controller
 * @author hub
 * @version 2018-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/api/accountInfo")
public class AccountInfoApi extends BaseController {

	@Autowired
	private AccountInfoService accountInfoService;

	@Autowired
	private CommissionInfoService commissionInfoService;

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private SettlementInfoService settlementInfoService;

	@ResponseBody
	@RequestMapping(value = "getList", method = RequestMethod.POST)
	public Result getList(HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setUserId(user.getId());
		//收入
		if("1".equals(request.getParameter("condition"))){
			//收支类型 1：收入 2：支出
			accountInfo.setType("1");
		}
		//支出
		if("2".equals(request.getParameter("condition"))){
			accountInfo.setType("2");
		}
		//待到账
		if("3".equals(request.getParameter("condition"))){
			accountInfo.setStatus(request.getParameter("2"));
		}

		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if(!StringUtils.isEmpty(startDate)){
			accountInfo.setStartDate(DateUtils.parseDate(startDate));
		}
		if(!StringUtils.isEmpty(endDate)){
			accountInfo.setEndDate(DateUtils.parseDate(endDate));
		}
		Page<AccountInfo> page = new Page<AccountInfo>(request,response);
		page= accountInfoService.getAccountInfos(accountInfo,page);

		JSONObject result = new JSONObject();
		result.put("pageNo",page.getPageNo());
		result.put("pageSize",page.getPageSize());
		result.put("list",page.getList());
		//收入 income 支出 expenditure 未到账 outAccount
		result.putAll(accountInfoService.getStsInfo(accountInfo));
		return ResultGenerator.genSuccessResult(result);
	}

	@ResponseBody
	@RequestMapping(value = "getDetails", method = RequestMethod.POST)
	public Result getDetails(HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setUserId(user.getId());
		accountInfo.setId(request.getParameter("id"));
		accountInfo = accountInfoService.getByApi(accountInfo);
		// 收支方式（1：佣金收益 2：销售收益） 支出（3：提现 4：结算）
		if("1".equals(accountInfo.getWay()) || "3".equals(accountInfo.getWay())){
			CommissionInfo commissionInfo = commissionInfoService.get(accountInfo.getUnionId());
			User produceUser = UserUtils.get(commissionInfo.getProduceUserId());
			if(null != produceUser){
				commissionInfo.setProduceUserName(produceUser.getNickname());
			}
			accountInfo.setCommissionInfo(commissionInfo);
		}
		if("2".equals(accountInfo.getWay()) || "4".equals(accountInfo.getWay())){
			OrderInfo orderInfo =orderInfoService.get(accountInfo.getUnionId());
			accountInfo.setOrderInfo(orderInfo);
		}
		return ResultGenerator.genSuccessResult(accountInfo);
	}
}