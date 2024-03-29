package com.mall.modules.gift.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.gift.entity.GiftConfigCategory;
import com.mall.modules.gift.entity.GiftConfigGoods;
import com.mall.modules.gift.service.GiftConfigCategoryService;
import com.mall.modules.sys.utils.UserUtils;
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

import static com.mall.common.service.BaseService.dataScopeFilter;

/**
 * 礼包配置Controller
 * @author wankang
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftConfig")
public class GiftConfigController extends BaseController {

	@Autowired
	private GiftConfigService giftConfigService;
	@Autowired
	private GiftConfigCategoryService giftConfigCategoryService;
	
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
		GiftConfigCategory queryCondition = new GiftConfigCategory();
		queryCondition.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "uoo", "uo"));
		List<GiftConfigCategory> giftConfigCategoryList = giftConfigCategoryService.findSelectListByPower(queryCondition);
		model.addAttribute("giftConfigCategoryList", giftConfigCategoryList);
		model.addAttribute("page", page);
		return "modules/gift/giftConfigList";
	}

	@RequiresPermissions("gift:giftConfig:view")
	@RequestMapping(value = "form")
	public String form(GiftConfig giftConfig, Model model) {
		GiftConfigCategory queryCondition = new GiftConfigCategory();
		queryCondition.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "uoo", "uo"));
		List<GiftConfigCategory> giftConfigCategoryList = giftConfigCategoryService.findSelectListByPower(queryCondition);
		model.addAttribute("giftConfigCategoryList", giftConfigCategoryList);
		model.addAttribute("giftConfig", giftConfig);
		return "modules/gift/giftConfigForm";
	}

	@RequiresPermissions("gift:giftConfig:edit")
	@RequestMapping(value = "save")
	public String save(GiftConfig giftConfig, Model model, RedirectAttributes redirectAttributes) {
		List<GiftConfigGoods> giftConfigGoods = giftConfig.getGiftConfigGoodsList();
		int goodsCount = 0;
		int couponCount = 0;
		if(null != giftConfigGoods && giftConfigGoods.size() > 0) {
			for (GiftConfigGoods g : giftConfigGoods) {
				goodsCount += g.getGoodsCount();
			}
		}else {
			model.addAttribute("message", "未选择商品");
			return form(giftConfig, model);
		}
		giftConfig.setGoodsCount(goodsCount);
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