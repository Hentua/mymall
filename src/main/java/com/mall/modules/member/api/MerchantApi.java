package com.mall.modules.member.api;

import com.alibaba.fastjson.JSONObject;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.Result;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.TreeNode;
import com.mall.common.web.BaseController;
import com.mall.modules.goods.entity.GoodsCategory;
import com.mall.modules.goods.service.GoodsCategoryService;
import com.mall.modules.goods.service.GoodsInfoService;
import com.mall.modules.member.entity.MemberBankAccount;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.entity.MerchantCollectionInfo;
import com.mall.modules.member.service.MemberBankAccountService;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.member.service.MerchantCollectionInfoService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 店铺Controller
 * @author hub
 * @version 2018-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/api")
public class MerchantApi extends BaseController {

	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private GoodsInfoService goodsInfoService;

	@Autowired
	private GoodsCategoryService goodsCategoryService;

	@Autowired
	private MerchantCollectionInfoService merchantCollectionInfoService;

	@Autowired
	private MemberBankAccountService memberBankAccountService;



	/**
	 * 店铺首页 明细
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/merchant/info", method = RequestMethod.POST)
	public void info(HttpServletRequest request, HttpServletResponse response) {
		String merchantId = request.getParameter("merchantId");
		User user = UserUtils.getUser();
		MemberInfo m = new MemberInfo();
		m.setId(merchantId);
		m = memberInfoService.get(m);
		JSONObject result = new JSONObject();
		result.put("merchantHeadImg",Global.getConfig("userfiles.baseURL") +m.getMerchantHeadImg());
		result.put("nickname",m.getNickname());
		result.put("merchantServicePhone",m.getMerchantServicePhone());
		result.put("isCollection","0");
		result.put("avatar",Global.getConfig("userfiles.baseURL") +m.getAvatar());
		if(null != user){
			MerchantCollectionInfo mc = new MerchantCollectionInfo();
			mc.setUserId(user.getId());
			List<MerchantCollectionInfo> list= merchantCollectionInfoService.findList(mc);
			if(null != list && list.size()>0){
				result.put("isCollection","1");
			}
		}
		result.putAll(goodsInfoService.merchantCountt(merchantId));
		renderString(response, ResultGenerator.genSuccessResult(result));
	}

	/**
	 * 店铺分类
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/merchant/goodsCategory", method = RequestMethod.POST)
	public void goodsCategory(HttpServletRequest request, HttpServletResponse response) {
		String merchantId = request.getParameter("merchantId");
		List<GoodsCategory> list = goodsCategoryService.findMerchantList(merchantId);
//		TreeNode<GoodsCategory> tree=new TreeNode<GoodsCategory>(list);
		renderString(response, ResultGenerator.genSuccessResult(list));
	}

	/**
	 * 店铺收藏or取消收藏
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/merchantCollection", method = RequestMethod.POST)
	public void merchantCollection(HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		String merchantId = request.getParameter("merchantId");
		MerchantCollectionInfo mc = new MerchantCollectionInfo();
		mc.setUserId(user.getId());
		List<MerchantCollectionInfo> list= merchantCollectionInfoService.findList(mc);
		if(null != list && list.size()>0){
			merchantCollectionInfoService.delete(list.get(0));
		}else{
			mc.setMerchantId(merchantId);
			merchantCollectionInfoService.save(mc);
		}
		renderString(response, ResultGenerator.genSuccessResult());
	}


	/**
	 * 店铺收藏or取消收藏
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/merchantCollectionList", method = RequestMethod.POST)
	public void merchantCollectionList(HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(user.getId());
		Page<MemberInfo> page = memberInfoService.findCollectionPage(new Page<MemberInfo>(request, response), memberInfo);
		renderString(response, ResultGenerator.genSuccessResult(page));
	}


	/**
	 * 用户银行卡列表接口
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/member/memberBankList", method = RequestMethod.POST)
	public void memberBankList(HttpServletRequest request, HttpServletResponse response){
		User user = UserUtils.getUser();
		MemberBankAccount memberBankAccount = new MemberBankAccount();
		memberBankAccount.setUserId(user.getId());
		List<MemberBankAccount> list = memberBankAccountService.findList(memberBankAccount);
		renderString(response, ResultGenerator.genSuccessResult(list));
	}


	/**
	 * 绑定银行卡接口
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/member/memberBankAdd", method = RequestMethod.POST)
	public void memberBankAdd(HttpServletRequest request, HttpServletResponse response){
		try{
			User user = UserUtils.getUser();
			String bankAccount = request.getParameter("bankAccount");
			String bankAccountName = request.getParameter("bankAccountName");
			String bankAddress = request.getParameter("bankAddress");
			MemberBankAccount memberBankAccount = new MemberBankAccount();
			memberBankAccount.setUserId(user.getId());
			memberBankAccount.setBankAccount(bankAccount);
			List<MemberBankAccount> memberBankAccounts = memberBankAccountService.findList(memberBankAccount);
			if(null !=memberBankAccounts && memberBankAccounts.size()>0){
				renderString(response, ResultGenerator.genFailResult("该卡已绑定"));
			}
			memberBankAccount.setBankAccountName(bankAccountName);
			memberBankAccount.setBankAddress(bankAddress);
			memberBankAccountService.save(memberBankAccount);
		}catch (Exception e){
			e.printStackTrace();
			renderString(response, ResultGenerator.genFailResult("绑定银行卡失败"));
		}
		renderString(response, ResultGenerator.genSuccessResult("绑定银行卡成功"));
	}

	/**
	 * 绑定银行卡接口
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/member/memberBankDel", method = RequestMethod.POST)
	public void memberBankDel(HttpServletRequest request, HttpServletResponse response){
		try{
			String bankAccountId = request.getParameter("bankAccountId");
			User user = UserUtils.getUser();
			MemberBankAccount memberBankAccount = new MemberBankAccount();
			memberBankAccount.setId(bankAccountId);
			memberBankAccount.setUserId(user.getId());
			memberBankAccountService.delete(memberBankAccount);
		}catch (Exception e){
			e.printStackTrace();
			renderString(response, ResultGenerator.genFailResult("解绑银行卡失败"));
		}
		renderString(response, ResultGenerator.genSuccessResult("解绑银行卡成功"));
	}












}