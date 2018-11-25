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
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.entity.MerchantCollectionInfo;
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



	/**
	 * 店铺首页 明细
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/merchant/info", method = RequestMethod.POST)
	public Result info(HttpServletRequest request, HttpServletResponse response) {
		String merchantId = request.getParameter("merchantId");
		User user = UserUtils.getUser();
		MemberInfo m = new MemberInfo();
		m.setId(merchantId);
		m = memberInfoService.get(m);
		JSONObject result = new JSONObject();
		result.put("merchantHeadImg",m.getMerchantHeadImg());
		result.put("nickname",m.getNickname());
		result.put("merchantServicePhone",m.getMerchantServicePhone());
		result.put("isCollection","0");
		if(null != user){
			MerchantCollectionInfo mc = new MerchantCollectionInfo();
			mc.setUserId(user.getId());
			List<MerchantCollectionInfo> list= merchantCollectionInfoService.findList(mc);
			if(null != list && list.size()>0){
				result.put("isCollection","1");
			}
		}
		result.putAll(goodsInfoService.merchantCountt(merchantId));
		return ResultGenerator.genSuccessResult(result);
	}

	/**
	 * 店铺分类
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/merchant/goodsCategory", method = RequestMethod.POST)
	public Result goodsCategory(HttpServletRequest request, HttpServletResponse response) {
		String merchantId = request.getParameter("merchantId");
		List<GoodsCategory> list = goodsCategoryService.findMerchantList(merchantId);
		TreeNode<GoodsCategory> tree=new TreeNode<GoodsCategory>(list);
		return ResultGenerator.genSuccessResult(tree.TreeFormat());
	}

	/**
	 * 店铺收藏or取消收藏
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/merchantCollection", method = RequestMethod.POST)
	public Result merchantCollection(HttpServletRequest request, HttpServletResponse response) {
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
		return ResultGenerator.genSuccessResult();
	}


	/**
	 * 店铺收藏or取消收藏
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/merchantCollectionList", method = RequestMethod.POST)
	public Result merchantCollectionList(HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setId(user.getId());
		Page<MemberInfo> page = memberInfoService.findCollectionPage(new Page<MemberInfo>(request, response), memberInfo);
		return ResultGenerator.genSuccessResult(page);
	}








}