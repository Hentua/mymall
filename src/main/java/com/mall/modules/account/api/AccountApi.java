package com.mall.modules.account.api;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mall.common.persistence.Page;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.IpUtil;
import com.mall.common.utils.Result;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.web.BaseController;
import com.mall.modules.account.entity.AccountFlow;
import com.mall.modules.account.service.AccountFlowService;
import com.mall.modules.account.service.AccountService;
import com.mall.modules.commission.entity.CommissionInfo;
import com.mall.modules.commission.entity.CommissionTakeOut;
import com.mall.modules.commission.service.CommissionInfoService;
import com.mall.modules.commission.service.CommissionTakeOutService;
import com.mall.modules.coupon.service.CouponCustomerService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.order.entity.OrderPaymentInfo;
import com.mall.modules.order.service.OrderPaymentInfoService;
import com.mall.modules.order.service.OrderWeixinExpenditureCallbackService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import com.sohu.idcenter.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;

/**
 * 账户明细Controller
 *
 * @author hub
 * @version 2018-10-21
 */
@Controller
@RequestMapping(value = "${adminPath}/api/account")
public class AccountApi extends BaseController {

    private static IdWorker idWorker = new IdWorker();

    @Autowired
    private AccountFlowService accountFlowService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CommissionInfoService commissionInfoService;

    @Autowired
    private CommissionTakeOutService commissionTakeOutService;

    @Autowired
    private OrderPaymentInfoService orderPaymentInfoService;

    @Autowired
    private CouponCustomerService couponCustomerService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private OrderWeixinExpenditureCallbackService orderWeixinExpenditureCallbackService;

    /**
     * 账户余额信息
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public Result info(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        MemberInfo m = new MemberInfo();
        m.setId(user.getId());
        MemberInfo memberInfo = memberInfoService.get(m);
        JSONObject result = new JSONObject();
        result.put("balance", memberInfo.getBalance());
        result.put("commission", memberInfo.getCommission());
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 账户余额信息
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Result list(HttpServletRequest request, HttpServletResponse response) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        User user = UserUtils.getUser();
        MemberInfo m = new MemberInfo();
        m.setId(user.getId());
        MemberInfo memberInfo = memberInfoService.get(m);
        AccountFlow accountFlow = new AccountFlow();
        accountFlow.setUserId(user.getId());
        accountFlow.setCheckStatus("2");
        Page<AccountFlow> page = new Page<AccountFlow>(request, response);
        page = accountFlowService.getAccountFlows(accountFlow, page);
        JSONObject result = new JSONObject();
        result.put("balance", decimalFormat.format(memberInfo.getBalance()));
        result.put("commission", decimalFormat.format(memberInfo.getCommission()));
        result.put("list", page.getList());
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        result.put("count", page.getCount());
        result.putAll(accountFlowService.stsFlow(accountFlow));
        return ResultGenerator.genSuccessResult(result);
    }


    /**
     * 账户余额信息
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "commissionList", method = RequestMethod.POST)
    public Result commissionList(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        MemberInfo m = new MemberInfo();
        m.setId(user.getId());
        MemberInfo memberInfo = memberInfoService.get(m);
        CommissionInfo c = new CommissionInfo();
        c.setUserId(memberInfo.getId());
        Page<CommissionInfo> page = new Page<>(request, response);
        page = commissionInfoService.findPage(page, c);
        return ResultGenerator.genSuccessResult(page);
    }


    /**
     * 充值
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    public Result recharge(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        String amount = request.getParameter("amount");
        if (StringUtils.isBlank(amount)) {
            return ResultGenerator.genFailResult("不合法的充值金额");
        }
        AccountFlow accountFlow = new AccountFlow();
        accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
        accountFlow.setUserId(user.getId());
        accountFlow.setAmount(Double.valueOf(amount));
        accountFlow.setType("1");//收入
        accountFlow.setMode("1");//充值
        accountFlow.setIncomeExpenditureMode(request.getParameter("incomeExpenditureMode"));// 收支方式 1：微信 2：用户转账
        accountFlow.setBankAccount(request.getParameter("bankAccount"));//银行账户
        accountFlow.setBankAccountName(request.getParameter("bankAccountName"));//开户人名称
        accountFlow.setBankName(request.getParameter("bankName"));//开户行
        accountFlow.setCheckStatus("1");
        accountFlowService.save(accountFlow);
        return ResultGenerator.genSuccessResult("成功");
    }

    /**
     * 微信充值
     *
     * @param request  请求体
     * @param response 响应体
     */
    @ResponseBody
    @RequestMapping(value = "wxRecharge", method = RequestMethod.POST)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void wxRecharge(HttpServletRequest request, HttpServletResponse response) {
        String amount = request.getParameter("amount");
        User currUser = UserUtils.getUser();
        try {
            if (StringUtils.isBlank(amount)) {
                throw new ServiceException("充值金额不能为空");
            }
            Double amountDouble = Double.valueOf(amount);
            if (amountDouble <= 0) {
                throw new ServiceException("不合法的充值金额");
            }
            OrderPaymentInfo orderPaymentInfo = orderPaymentInfoService.genAmountPaymentInfo("0", "2", amountDouble, 0.00);
            AccountFlow accountFlow = new AccountFlow();
            accountFlow.setFlowNo(orderPaymentInfo.getPaymentNo());
            accountFlow.setUserId(currUser.getId());
            accountFlow.setAmount(amountDouble);
            accountFlow.setType("1");//收入
            accountFlow.setMode("1");//充值
            accountFlow.setIncomeExpenditureMode("1");// 收支方式 1：微信 2：用户转账
            accountFlow.setCheckStatus("1");
            accountFlowService.save(accountFlow);
            renderString(response, ResultGenerator.genSuccessResult(orderPaymentInfo));
        } catch (NumberFormatException e) {
            renderString(response, ResultGenerator.genFailResult("不合法的充值金额"));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }


    /**
     * 提现
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "expenditure", method = RequestMethod.POST)
    public void expenditure(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        String incomeExpenditureMode = request.getParameter("incomeExpenditureMode");
        String amountStr = request.getParameter("amount");
        Double amount = Double.valueOf(amountStr);
        MemberInfo m = new MemberInfo();
        m.setId(user.getId());
        MemberInfo memberInfo = memberInfoService.get(m);
        try {
            if (memberInfo.getBalance() < amount) {
                throw new ServiceException("账户余额不足");
            }
            AccountFlow accountFlow = new AccountFlow();
            accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
            accountFlow.setUserId(user.getId());
            accountFlow.setAmount(amount);
            accountFlow.setType("2");//支出
            accountFlow.setMode("3");//提现
            accountFlow.setIncomeExpenditureMode(incomeExpenditureMode);// 收支方式 1：微信 2：用户转账
            accountFlow.setBankAccount(request.getParameter("bankAccount"));//银行账户
            accountFlow.setBankAccountName(request.getParameter("bankAccountName"));//开户人名称
            accountFlow.setBankName(request.getParameter("bankName"));//开户行
            accountFlow.setCheckStatus("1");
            accountFlowService.save(accountFlow);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 微信提现
     *
     * @param request  请求体
     * @param response 响应体
     */
    @ResponseBody
    @RequestMapping(value = "wxExpenditure", method = RequestMethod.POST)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void wxExpenditure(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        String id = user.getId();
        MemberInfo queryCondition = new MemberInfo();
        queryCondition.setId(id);
        MemberInfo memberInfo = memberInfoService.get(queryCondition);
        String amountStr = request.getParameter("amount");
        Double amount = Double.valueOf(amountStr);
        try {
            String openId = memberInfo.getOpenid();
            if (StringUtils.isBlank(openId)) {
                throw new ServiceException("您还未绑定微信，不能申请提现");
            }
            if (memberInfo.getBalance() < amount) {
                throw new ServiceException("账户余额不足");
            }
            AccountFlow accountFlow = new AccountFlow();
            accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
            accountFlow.setUserId(user.getId());
            accountFlow.setAmount(amount);
            accountFlow.setType("2");//支出
            accountFlow.setMode("3");//提现
            accountFlow.setIncomeExpenditureMode("1");// 收支方式 1：微信 2：用户转账
            accountFlow.setCheckStatus("2");
            accountFlowService.save(accountFlow);
            Double balance = memberInfo.getBalance() - amount;
            if (balance < 0) {
                throw new ServiceException("账户余额不足");
            }
            memberInfo.setBalance(balance);
            accountService.editAccount(balance, null, memberInfo.getId());
            EntPayRequest entPayRequest = new EntPayRequest();
            entPayRequest.setPartnerTradeNo(accountFlow.getFlowNo());
            entPayRequest.setOpenid(openId);
            entPayRequest.setCheckName("NO_CHECK");
            entPayRequest.setAmount(BaseWxPayRequest.yuanToFen(String.valueOf(amount)));
            entPayRequest.setDescription("美易优选-提现");
            entPayRequest.setSpbillCreateIp(IpUtil.getIpAddress(request));
            EntPayResult entPayResult = wxPayService.getEntPayService().entPay(entPayRequest);
            if (!"SUCCESS".equalsIgnoreCase(entPayResult.getReturnCode()) || !"SUCCESS".equalsIgnoreCase(entPayResult.getResultCode())) {
                throw new ServiceException("微信处理失败");
            } else {
                orderWeixinExpenditureCallbackService.save(entPayResult);
            }
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }


    /**
     * 佣金转余额
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "CommissionTransferBalance", method = RequestMethod.POST)
    public Result CommissionTransferBalance(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = UserUtils.getUser();
        MemberInfo m = new MemberInfo();
        m.setId(user.getId());
        MemberInfo memberInfo = memberInfoService.get(m);
        String amountStr = request.getParameter("amount");
        Double amount = Double.valueOf(amountStr);
        if (memberInfo.getCommission() < amount) {
            return ResultGenerator.genFailResult("佣金余额不足");
        }
        AccountFlow accountFlow = new AccountFlow();
        accountFlow.setFlowNo(String.valueOf(idWorker.getId()));
        accountFlow.setUserId(user.getId());
        accountFlow.setAmount(amount);
        accountFlow.setType("1");//收入
        accountFlow.setMode("2");//佣金转余额
//		accountFlow.setIncomeExpenditureMode(request.getParameter("incomeExpenditureMode"));// 收支方式 1：微信 2：用户转账
//		accountFlow.setBankAccount(request.getParameter("bankAccount"));//银行账户
//		accountFlow.setBankAccountName(request.getParameter("bankAccountName"));//开户人名称
//		accountFlow.setBankName(request.getParameter("bankName"));//开户行
        accountFlow.setCheckStatus("1");
        accountFlowService.save(accountFlow);
        //送优惠券函数
        couponCustomerService.saveCouponCustomerByPlatform(amount, "0", user.getId(), "佣金转余额优惠券", "3");
        //操作余额
        accountService.editAccount(memberInfo.getBalance() + amount, memberInfo.getCommission() - amount, user.getId());
        return ResultGenerator.genSuccessResult("成功");
    }

    /**
     * 佣金提现
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "commissionTakeOut", method = RequestMethod.POST)
    public Result CommissionTakeOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = UserUtils.getUser();
        MemberInfo m = new MemberInfo();
        m.setId(user.getId());
        MemberInfo memberInfo = memberInfoService.get(m);
        String amountStr = request.getParameter("amount");
        Double amount = Double.valueOf(amountStr);
        if (memberInfo.getCommission() < amount) {
            return ResultGenerator.genFailResult("佣金余额不足");
        }
        //新增提现记录
        CommissionTakeOut commissionTakeOut = new CommissionTakeOut();
        commissionTakeOut.setAmount(amount);
        commissionTakeOut.setUserId(user.getId());
        commissionTakeOut.setBankAccount(request.getParameter("bankAccount"));
        commissionTakeOut.setBankAccountName(request.getParameter("bankAccountName"));
        commissionTakeOut.setBankName(request.getParameter("bankName"));
        commissionTakeOut.setCheckStatus("1");
        commissionTakeOutService.save(commissionTakeOut);
        return ResultGenerator.genSuccessResult("成功");
    }


}