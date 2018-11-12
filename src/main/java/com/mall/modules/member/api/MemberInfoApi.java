package com.mall.modules.member.api;

import com.google.common.collect.Maps;
import com.mall.common.persistence.Page;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.EhCacheUtils;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.ResultStatus;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.utils.api.ApiPageEntityHandleUtil;
import com.mall.common.web.BaseController;
import com.mall.modules.coupon.service.CouponCustomerService;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.service.GoodsInfoService;
import com.mall.modules.member.entity.*;
import com.mall.modules.member.service.MemberDeliveryAddressService;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.member.service.MemberVerifyCodeService;
import com.mall.modules.order.service.OrderInfoService;
import com.mall.modules.sys.entity.Office;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.entity.UserVo;
import com.mall.modules.sys.service.SystemService;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 会员接口
 *
 * @author wankang
 * @since 2018-10-11
 */
@RestController
@RequestMapping(value = "${adminPath}/api")
public class MemberInfoApi extends BaseController {

    @Autowired
    private MemberVerifyCodeService memberVerifyCodeService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private MemberDeliveryAddressService memberDeliveryAddressService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private CouponCustomerService couponCustomerService;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void memberRegister(HttpServletRequest request, HttpServletResponse response) {
        // 解析前端数据
        String mobile = request.getParameter("mobile");
        String verifyCode = request.getParameter("verifyCode");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String refereeCode = request.getParameter("refereeCode");
        String sex = request.getParameter("sex");
        String nickname = request.getParameter("nickname");
        try {
            // 数据验证
            if (StringUtils.isBlank(mobile)) {
                throw new ServiceException("手机号不能为空");
            } else if (StringUtils.isBlank(verifyCode)) {
                throw new ServiceException("验证码不能为空");
            } else if (StringUtils.isBlank(password)) {
                throw new ServiceException("密码不能为空");
            } else if (StringUtils.isBlank(repeatPassword)) {
                throw new ServiceException("密码不能为空");
            } else if (StringUtils.isBlank(refereeCode)) {
                throw new ServiceException("邀请人不能为空");
            } else if (StringUtils.isBlank(nickname)) {
                throw new ServiceException("用户昵称不能为空");
            } else if (nickname.length() > 20) {
                throw new ServiceException("用户昵称最多不能超过20");
            } else {
                if (!MemberInfoService.isPhone(mobile)) {
                    throw new ServiceException("手机号码格式不正确");
                }
            }
            boolean validResult = memberVerifyCodeService.validVerifyCode(mobile, verifyCode, "0");
            if (!validResult) {
                throw new ServiceException("验证码错误");
            }
            if (!password.equals(repeatPassword)) {
                throw new ServiceException("两次输入密码不一致");
            }
            if (UserUtils.getByLoginName(mobile) != null) {
                throw new ServiceException("用户已存在");
            }
            MemberInfo condition = new MemberInfo();
            condition.setReferee(refereeCode);
            MemberInfo refereeInfo = memberInfoService.get(condition);
            if (null == refereeInfo) {
                throw new ServiceException("邀请码无效");
            }
            String refereeId = refereeInfo.getId();

            // 初始化SysUser实体数据
            User user = new User();
            user.setLoginName(mobile);
            user.setMobile(mobile);
            // 普通会员固定归属公司 ID为1000
            user.setCompany(new Office("1000"));
            user.setOffice(new Office("1000"));
            user.setPassword(SystemService.entryptPassword(password));
            user.setName(nickname);
            user.setUserType("0");
            // 刚刚注册普通会员不赋予角色
//            List<Role> roleList = Lists.newArrayList();
//            roleList.add(new Role("1000"));
//            user.setRoleList(roleList);
            // 保存用户信息
            systemService.saveUser(user);
            // 清除当前用户缓存
            if (user.getLoginName().equals(UserUtils.getUser().getLoginName())) {
                UserUtils.clearCache();
            }

            // 初始化会员信息
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setNickname(nickname);
            memberInfo.setRefereeId(refereeId);
            memberInfo.setRegisterWay("0");
            memberInfo.setSex(sex);
            memberInfo.setBalance(0.00);
            memberInfo.setStatus("0");
            memberInfo.preInsert();
            memberInfo.setId(user.getId());
            memberInfo.setReferee(MemberInfoService.genRefereeId());
            memberInfo.setIsNewRecord(true);
            memberInfoService.save(memberInfo);
            renderString(response, ResultGenerator.genSuccessResult("注册成功"));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    @RequestMapping(value = "genVerifyCode", method = RequestMethod.POST)
    public void genVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        String mobile = request.getParameter("mobile");
        // type 为0时发送注册验证码 为1时发送忘记密码验证码 为2时为修改敏感信息验证码
        String type = request.getParameter("type");
        try {
            if (StringUtils.isBlank(mobile)) {
                throw new ServiceException("手机号不能为空");
            } else {
                if (!MemberInfoService.isPhone(mobile)) {
                    throw new ServiceException("手机号码格式不正确");
                }
            }
            memberVerifyCodeService.sendVerifyCodeSms(mobile, type);
            renderString(response, ResultGenerator.genSuccessResult("验证码发送成功"));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 修改用户个人信息
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "modifyMemberInfo", method = RequestMethod.POST)
    public void modifyMemberInfo(HttpServletRequest request, HttpServletResponse response) {
        String nickname = request.getParameter("nickname");
        String sex = request.getParameter("sex");
        User currUser = UserUtils.getUser();
        try {
            if (StringUtils.isBlank(nickname)) {
                throw new ServiceException("昵称不能为空");
            }
            if (StringUtils.isBlank(sex)) {
                throw new ServiceException("性别不能为空");
            }
            if (!"0".equals(sex) && !"1".equals(sex)) {
                throw new ServiceException("性别不正确");
            }
            if (nickname.length() > 20) {
                throw new ServiceException("用户昵称最多不能超过20");
            }
            currUser.setName(nickname);
            systemService.updateUserInfo(currUser);
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(currUser.getId());
            memberInfo.setNickname(nickname);
            memberInfo.setSex(sex);
            memberInfoService.save(memberInfo);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 判断用户是否创建支付密码 返回状态码200则为创建 返回状态码为601则为未创建
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "whetherPayPassword", method = RequestMethod.POST)
    public void whetherPayPassword(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String id = currUser.getId();
        try {
            String payPassword = memberInfoService.getPayPassword(id);
            if (StringUtils.isBlank(payPassword)) {
                renderString(response, ResultGenerator.genFailResult("用户未创建支付密码").setStatus(ResultStatus.NULL_PAY_PASSWORD));
            } else {
                renderString(response, ResultGenerator.genSuccessResult());
            }
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 设置支付密码
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "setPayPassword", method = RequestMethod.POST)
    public void setPayPassword(HttpServletRequest request, HttpServletResponse response) {
        String payPassword = request.getParameter("payPassword");
        User currUser = UserUtils.getUser();
        try {
            String id = currUser.getId();
            String oldPayPassword = memberInfoService.getPayPassword(id);
            if (StringUtils.isNotBlank(oldPayPassword)) {
                throw new ServiceException("支付密码已存在，不可重复设置");
            }
            if (!MemberInfoService.validatePayPasswordFormat(payPassword)) {
                throw new ServiceException("支付密码格式不正确");
            }
            String cipherPayPassword = SystemService.entryptPassword(payPassword);
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(id);
            memberInfo.setPayPassword(cipherPayPassword);
            memberInfoService.savePayPassword(memberInfo);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 修改支付密码
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "modifyPayPassword", method = RequestMethod.POST)
    public void modifyPayPassword(HttpServletRequest request, HttpServletResponse response) {
        String oldPayPassword = request.getParameter("oldPayPassword");
        String newPayPassword = request.getParameter("payPassword");
        User currUser = UserUtils.getUser();
        try {
            // 验证老支付密码
            if (!memberInfoService.validatePayPassword(oldPayPassword, currUser.getId())) {
                throw new ServiceException("支付密码错误");
            }
            // 验证新支付密码格式
            if (!MemberInfoService.validatePayPasswordFormat(newPayPassword)) {
                throw new ServiceException("支付密码格式不正确");
            }
            String cipherPayPassword = SystemService.entryptPassword(newPayPassword);
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(currUser.getId());
            memberInfo.setPayPassword(cipherPayPassword);
            memberInfoService.savePayPassword(memberInfo);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 修改手机号码
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "modifyMemberMobile", method = RequestMethod.POST)
    public void modifyMemberMobile(HttpServletRequest request, HttpServletResponse response) {
        String mobile = request.getParameter("mobile");
        String verifyCode = request.getParameter("verifyCode");
        User currUser = UserUtils.getUser();
        try {
            if (!"0".equals(currUser.getUserType()) && !"1".equals(currUser.getUserType())) {
                throw new ServiceException("不支持该用户类型");
            }
            if (StringUtils.isBlank(mobile)) {
                throw new ServiceException("手机号不能为空");
            } else {
                if (!MemberInfoService.isPhone(mobile)) {
                    throw new ServiceException("手机号码格式不正确");
                }
            }
            boolean validResult = memberVerifyCodeService.validVerifyCode(mobile, verifyCode, "2");
            if (!validResult) {
                throw new ServiceException("验证码错误");
            }
            User user = UserUtils.getByLoginName(mobile);
            if (null != user) {
                throw new ServiceException("该用户已存在");
            }
            currUser.setMobile(mobile);
            currUser.setLoginName(mobile);
            systemService.saveUser(currUser);
            UserUtils.clearCache();
            String token = UserUtils.getTokenStr(request);
            EhCacheUtils.remove(token);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 个人收获地址添加
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "addDeliveryAddress", method = RequestMethod.POST)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void addDeliveryAddress(HttpServletRequest request, HttpServletResponse response) {
        try {
            MemberDeliveryAddress memberDeliveryAddress = memberDeliveryAddressService.genMemberDeliveryAddress(request);
            if (null == memberDeliveryAddress) {
                throw new ServiceException("手机号格式不正确");
            }
            beanValidator(memberDeliveryAddress);
            MemberDeliveryAddress condition = new MemberDeliveryAddress();
            condition.setCustomerCode(memberDeliveryAddress.getCustomerCode());
            List<MemberDeliveryAddress> memberDeliveryAddresses = memberDeliveryAddressService.findList(condition);
            memberDeliveryAddressService.save(memberDeliveryAddress);
            if (memberDeliveryAddresses.size() > 0 && "1".equalsIgnoreCase(memberDeliveryAddress.getIsDefaultAddress())) {
                memberDeliveryAddressService.modifyDefaultDeliveryAddress(memberDeliveryAddress.getCustomerCode(), memberDeliveryAddress.getId());
            } else if (memberDeliveryAddresses.size() <= 0) {
                memberDeliveryAddressService.modifyDefaultDeliveryAddress(memberDeliveryAddress.getCustomerCode(), memberDeliveryAddress.getId());
            }
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 获取默认收货地址
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "getDefaultDeliveryAddress", method = RequestMethod.POST)
    public void getDefaultDeliveryAddress(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            MemberDeliveryAddress condition = new MemberDeliveryAddress();
            condition.setCustomerCode(customerCode);
            condition.setIsDefaultAddress("1");
            List<MemberDeliveryAddress> memberDeliveryAddresses = memberDeliveryAddressService.findList(condition);
            if (memberDeliveryAddresses.size() > 0) {
                renderString(response, ResultGenerator.genSuccessResult(memberDeliveryAddresses.get(0)));
            } else {
                renderString(response, ResultGenerator.genFailResult("收货地址为空"));
            }
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 设置默认收货地址
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "modifyDefaultDeliveryAddress", method = RequestMethod.POST)
    public void modifyDefaultDeliveryAddress(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        String addressId = request.getParameter("id");
        try {
            if (StringUtils.isBlank(addressId)) {
                throw new ServiceException("未选择默认收货地址");
            }
            memberDeliveryAddressService.modifyDefaultDeliveryAddress(customerCode, addressId);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 获取收货地址列表
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "deliveryAddressList", method = RequestMethod.POST)
    public void deliveryAddressList(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            MemberDeliveryAddress condition = new MemberDeliveryAddress();
            condition.setCustomerCode(customerCode);
            List<MemberDeliveryAddress> memberDeliveryAddresses = memberDeliveryAddressService.findList(condition);
            renderString(response, ResultGenerator.genSuccessResult(memberDeliveryAddresses));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 删除收货地址 不能删除默认收货地址
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "deleteDeliveryAddress", method = RequestMethod.POST)
    public void deleteDeliveryAddress(HttpServletRequest request, HttpServletResponse response) {
        String addressId = request.getParameter("id");
        try {
            if (StringUtils.isBlank(addressId)) {
                throw new ServiceException("未选择要删除的收货地址");
            }
            if (null == memberDeliveryAddressService.get(addressId)) {
                throw new ServiceException("收货地址不存在");
            }
            int row = memberDeliveryAddressService.deleteAddress(addressId);
            if (row <= 0) {
                throw new ServiceException("不能删除默认收货地址");
            }
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 更新收货地址
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "updateDeliveryAddress", method = RequestMethod.POST)
    public void updateDeliveryAddress(HttpServletRequest request, HttpServletResponse response) {
        try {
            MemberDeliveryAddress memberDeliveryAddress = memberDeliveryAddressService.genMemberDeliveryAddress(request);
            if (null == memberDeliveryAddress) {
                throw new ServiceException("手机号格式不正确");
            }
            if (StringUtils.isBlank(memberDeliveryAddress.getId())) {
                throw new ServiceException("未选择要修改的收货地址");
            }
            beanValidator(memberDeliveryAddress);
            memberDeliveryAddressService.save(memberDeliveryAddress);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 获取省市区JSON数据
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "getAreaData", method = RequestMethod.POST)
    public void getAreaData(HttpServletRequest request, HttpServletResponse response) {
        renderString(response, ResultGenerator.genSuccessResult(UserUtils.getAreaList()));
    }

    /**
     * 获取推荐成功人列表
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "refereeList", method = RequestMethod.POST)
    public void refereeList(HttpServletRequest request, HttpServletResponse response) {
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        User currUser = UserUtils.getUser();
        String refereeId = currUser.getId();
        try {
            Page<MemberInfo> page = ApiPageEntityHandleUtil.packagePage(new Page<MemberInfo>(), pageNo, pageSize);
            MemberInfo queryCondition = new MemberInfo();
            queryCondition.setRefereeId(refereeId);
            Page<MemberInfo> memberInfoPage = memberInfoService.findPage(page, queryCondition);
            List<MemberInfo> memberInfos = memberInfoPage.getList();
            for (MemberInfo m : memberInfos) {
                m.setBalance(0.00);
            }
            renderString(response, ResultGenerator.genSuccessResult(memberInfos));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 根据member id获取会员信息
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "getMemberInfo", method = RequestMethod.POST)
    public void getMemberInfo(HttpServletRequest request, HttpServletResponse response) {
        String memberId = request.getParameter("memberId");
        try {
            if (StringUtils.isBlank(memberId)) {
                throw new ServiceException("会员ID不能为空");
            }
            User user = UserUtils.get(memberId);
            if (null == user) {
                throw new ServiceException("会员不存在");
            }
            renderString(response, ResultGenerator.genSuccessResult(new UserVo(user)));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 获取当前登录用户 订单、卡券、礼包统计数据
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "currMemberDataCount", method = RequestMethod.POST)
    public void currMemberDataCount(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            Map<String, String> memberDataCount = Maps.newHashMap();
            // 获取订单统计
            Map<String, String> orderCount = orderInfoService.orderCount(customerCode);
            // 获取优惠券统计
            Map<String, String> enabledCouponsCount = couponCustomerService.enabledCouponsCount(customerCode);

            memberDataCount.putAll(orderCount);
            memberDataCount.putAll(enabledCouponsCount);
            MemberInfo m = new MemberInfo();
            m.setId(currUser.getId());
            m = memberInfoService.get(m);
            memberDataCount.put("balance", m.getBalance() == null ? "0.0" : m.getBalance().toString());
            memberDataCount.put("commission", m.getCommission() == null ? "0.0" : m.getCommission().toString());
            renderString(response, ResultGenerator.genSuccessResult(memberDataCount));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 获取当前登录用户收藏商品列表
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "memberFavoriteList", method = RequestMethod.POST)
    public void memberFavoriteList(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            MemberFavorite queryCondition = new MemberFavorite();
            queryCondition.setCustomerCode(customerCode);
            List<MemberFavorite> memberFavorites = memberInfoService.findList(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult(memberFavorites));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 用户添加收藏
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "addFavorite", method = RequestMethod.POST)
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) {
        String goodsId = request.getParameter("goodsId");
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            if (StringUtils.isBlank(goodsId)) {
                throw new ServiceException("未选择要收藏的商品");
            }
            GoodsInfo goodsInfo = goodsInfoService.get(goodsId);
            if (null == goodsInfo) {
                throw new ServiceException("商品不存在");
            }
            MemberFavorite memberFavorite = new MemberFavorite();
            memberFavorite.setCustomerCode(customerCode);
            memberFavorite.setGoodsId(goodsId);
            List<MemberFavorite> existedList = memberInfoService.findList(memberFavorite);
            if (existedList.size() <= 0) {
                memberInfoService.addFavorite(memberFavorite);
            }
            renderString(response, ResultGenerator.genSuccessResult(memberFavorite));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 删除收藏
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "deleteFavorite", method = RequestMethod.POST)
    public void deleteFavorite(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        try {
            if (StringUtils.isBlank(id)) {
                throw new ServiceException("未选择要删除的收藏");
            }
            MemberFavorite deleteCondition = new MemberFavorite();
            deleteCondition.setId(id);
            memberInfoService.deleteFavorite(deleteCondition);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 会员提交反馈信息
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "submitFeedback", method = RequestMethod.POST)
    public void submitFeedback(HttpServletRequest request, HttpServletResponse response) {
        String feedback = request.getParameter("feedback");
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            if (StringUtils.isBlank(feedback)) {
                throw new ServiceException("您还没输入反馈信息");
            }
            if (feedback.length() > 500) {
                throw new ServiceException("您最多只能输入500字");
            }
            MemberFeedback memberFeedback = new MemberFeedback();
            memberFeedback.setCustomerCode(customerCode);
            memberFeedback.setFeedbackDetail(feedback);
            memberInfoService.submitFeedback(memberFeedback);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 查询会员反馈历史纪录列表
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "feedbackList", method = RequestMethod.POST)
    public void feedbackList(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            MemberFeedback queryCondition = new MemberFeedback();
            queryCondition.setCustomerCode(customerCode);
            List<MemberFeedback> memberFeedbackList = memberInfoService.findList(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult(memberFeedbackList));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 查询当前用户商品足迹
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "footprintList", method = RequestMethod.POST)
    public void footprintList(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String customerCode = currUser.getId();
        try {
            MemberFootprint queryCondition = new MemberFootprint();
            queryCondition.setCustomerCode(customerCode);
            List<MemberFootprint> memberFootprints = memberInfoService.findList(queryCondition);
            renderString(response, ResultGenerator.genSuccessResult(memberFootprints));
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 修改密码
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "modifyPassword", method = RequestMethod.POST)
    public void modifyPassword(HttpServletRequest request, HttpServletResponse response) {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String repeatPassword = request.getParameter("repeatPassword");
        User currUser = UserUtils.getUser();
        String userId = currUser.getId();
        try {
            if (StringUtils.isBlank(oldPassword)) {
                throw new ServiceException("旧密码不能为空");
            }
            if (StringUtils.isBlank(newPassword)) {
                throw new ServiceException("密码不能为空");
            }
            if (StringUtils.isBlank(repeatPassword)) {
                throw new ServiceException("请确认新密码");
            }
            String entryptOldPassword = SystemService.entryptPassword(oldPassword);
            User validateUser = new User();
            validateUser.setId(userId);
            validateUser.setPassword(entryptOldPassword);
            boolean flag = systemService.validatePassword(validateUser);
            if (!flag) {
                throw new ServiceException("用户密码错误");
            }
            if (!newPassword.equals(repeatPassword)) {
                throw new ServiceException("两次输入密码不一致");
            }
            systemService.updatePasswordById(userId, currUser.getLoginName(), newPassword);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 忘记密码 重置密码
     *
     * @param request  请求体
     * @param response 响应体
     */
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public void resetPassword(HttpServletRequest request, HttpServletResponse response) {
        String mobile = request.getParameter("mobile");
        String verifyCode = request.getParameter("verifyCode");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        try {
            if (StringUtils.isBlank(mobile)) {
                throw new ServiceException("手机号码不能为空");
            }
            if (!MemberInfoService.isPhone(mobile)) {
                throw new ServiceException("手机号码格式不正确");
            }
            User user = UserUtils.getByLoginName(mobile);
            if (null == user) {
                throw new ServiceException("用户不存在");
            }
            if (StringUtils.isBlank(verifyCode)) {
                throw new ServiceException("验证码不能为空");
            }
            if (!memberVerifyCodeService.validVerifyCode(mobile, verifyCode, "1")) {
                throw new ServiceException("验证码错误");
            }
            if (StringUtils.isBlank(password)) {
                throw new ServiceException("密码不能为空");
            }
            if (StringUtils.isBlank(repeatPassword)) {
                throw new ServiceException("请确认新密码");
            }
            if (!password.equals(repeatPassword)) {
                throw new ServiceException("两次输入密码不一致");
            }
            systemService.updatePasswordById(user.getId(), user.getLoginName(), password);
            renderString(response, ResultGenerator.genSuccessResult());
        } catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

}
