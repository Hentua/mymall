package com.mall.modules.gift.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.gift.entity.GiftMerchant;
import com.mall.modules.gift.entity.GiftMerchantGoods;
import com.mall.modules.gift.entity.GiftSaleLog;
import com.mall.modules.gift.service.GiftMerchantService;
import com.mall.modules.gift.service.GiftSaleLogService;
import com.mall.modules.sys.utils.UserUtils;
import com.sohu.idcenter.IdWorker;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.web.BaseController;
import com.mall.common.utils.StringUtils;
import com.mall.modules.gift.entity.GiftConfig;
import com.mall.modules.gift.service.GiftConfigService;

import java.util.List;

/**
 * 礼包配置Controller
 * @author wankang
 * @version 2018-10-28
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftConfig")
public class GiftConfigController extends BaseController {

	private static IdWorker idWorker = new IdWorker();
	@Autowired
	private GiftConfigService giftConfigService;
	@Autowired
	private GiftSaleLogService giftSaleLogService;
	@Autowired
	private GiftMerchantService giftMerchantService;
	
	@ModelAttribute
	public GiftConfig get(@RequestParam(required=false) String id) {
		GiftConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftConfigService.get(id);
		}
		if (entity == null){
			entity = new GiftConfig();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftConfig giftConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GiftConfig> page = giftConfigService.findPage(new Page<GiftConfig>(request, response), giftConfig); 
		model.addAttribute("page", page);
		return "modules/gift/giftConfigList";
	}

	@RequiresPermissions("gift:giftConfig:view")
	@RequestMapping(value = {"giftBuyList"})
	public String giftBuyList(GiftConfig giftConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GiftConfig> page = giftConfigService.findPage(new Page<GiftConfig>(request, response), giftConfig);
		model.addAttribute("page", page);
		return "modules/gift/giftBuy";
	}

	@RequiresPermissions("gift:giftConfig:view")
	@RequestMapping(value = "giftDetail")
	public String giftDetail(GiftConfig giftConfig, Model model) {
		model.addAttribute("giftConfig", giftConfig);
		return "modules/gift/giftConfigDetail";
	}

	@RequestMapping(value = "giftBuySubmit")
	public String giftBuySubmit(GiftConfig giftConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		String buyCountStr = request.getParameter("buyCount");
		int buyCount = Integer.parseInt(buyCountStr);
		String orderNo = String.valueOf(idWorker.getId());
		// 组装商户礼包信息
		GiftMerchant giftMerchant = new GiftMerchant();
		giftMerchant.setGiftConfigId(giftConfig.getId());
		giftMerchant.setGiftName(giftConfig.getGiftName());
		giftMerchant.setOriginalPrice(giftConfig.getOriginalPrice());
		giftMerchant.setGiftPrice(giftConfig.getGiftPrice());
		giftMerchant.setGoodsCount(giftConfig.getGoodsCount());
		giftMerchant.setGiftCount(buyCount);
		giftMerchant.setOrderNo(orderNo);
		giftMerchant.setDelFlag("1");
		giftMerchant.setMerchantCode(UserUtils.getUser().getId());
		List<GiftMerchantGoods> merchantGoodsList = giftMerchantService.translateConfigGoods(giftConfig.getGiftConfigGoodsList());
		giftMerchant.setGiftMerchantGoodsList(merchantGoodsList);
		giftMerchantService.save(giftMerchant);
		// 组装支付信息
		GiftSaleLog giftSaleLog = new GiftSaleLog();
		giftSaleLog.setOrderNo(orderNo);
		giftSaleLog.setFlag("0");
		giftSaleLog.setGiftConfigId(giftConfig.getId());
		giftSaleLog.setMerchantCode(UserUtils.getUser().getId());
		giftSaleLog.setGiftPrice(giftConfig.getGiftPrice());
		giftSaleLog.setGiftCount(buyCount);
		giftSaleLog.setGiftGoodsCount(giftConfig.getGoodsCount());
		giftSaleLog.setGiftName(giftConfig.getGiftName());
		giftSaleLog.setGiftAmountTotal(buyCount * giftConfig.getGiftPrice());
		giftSaleLog.setGiftMerchantId(giftMerchant.getId());
		giftSaleLogService.save(giftSaleLog);
		model.addAttribute("message", "提交成功，打款到公司财务账户，并备注订单号："+orderNo+"，审核后礼包将会到库");
		return this.giftBuyList(new GiftConfig(), request, response, model);
	}

	@RequiresPermissions("gift:giftConfig:view")
	@RequestMapping(value = "form")
	public String form(GiftConfig giftConfig, Model model) {
		model.addAttribute("giftConfig", giftConfig);
		return "modules/gift/giftConfigForm";
	}

	@RequiresPermissions("gift:giftConfig:edit")
	@RequestMapping(value = "save")
	public String save(GiftConfig giftConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftConfig)){
			return form(giftConfig, model);
		}
		giftConfigService.save(giftConfig);
		addMessage(redirectAttributes, "保存礼包配置成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftConfig/?repage";
	}
	
	@RequiresPermissions("gift:giftConfig:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftConfig giftConfig, RedirectAttributes redirectAttributes) {
		giftConfigService.delete(giftConfig);
		addMessage(redirectAttributes, "删除礼包配置成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftConfig/?repage";
	}

}