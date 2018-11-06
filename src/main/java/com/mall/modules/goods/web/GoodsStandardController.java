package com.mall.modules.goods.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.mall.modules.goods.entity.GoodsStandard;
import com.mall.modules.goods.service.GoodsStandardService;

/**
 * 商品规格Controller
 * @author hub
 * @version 2018-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/goods/goodsStandard")
public class GoodsStandardController extends BaseController {

	@Autowired
	private GoodsStandardService goodsStandardService;
	
	@ModelAttribute
	public GoodsStandard get(@RequestParam(required=false) String id) {
		GoodsStandard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsStandardService.get(id);
		}
		if (entity == null){
			entity = new GoodsStandard();
		}
		return entity;
	}
	
	@RequiresPermissions("goods:goodsStandard:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsStandard goodsStandard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsStandard> page = goodsStandardService.findPage(new Page<GoodsStandard>(request, response), goodsStandard); 
		model.addAttribute("page", page);
		return "modules/goods/goodsStandardList";
	}

	@RequiresPermissions("goods:goodsStandard:view")
	@RequestMapping(value = "form")
	public String form(GoodsStandard goodsStandard, Model model) {
		model.addAttribute("goodsStandard", goodsStandard);
		return "modules/goods/goodsStandardForm";
	}

	@RequiresPermissions("goods:goodsStandard:edit")
	@RequestMapping(value = "save")
	public String save(GoodsStandard goodsStandard, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsStandard)){
			return form(goodsStandard, model);
		}
		goodsStandardService.save(goodsStandard);
		addMessage(redirectAttributes, "保存商品规格成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsStandard/?repage";
	}
	
	@RequiresPermissions("goods:goodsStandard:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsStandard goodsStandard, RedirectAttributes redirectAttributes) {
		goodsStandardService.delete(goodsStandard);
		addMessage(redirectAttributes, "删除商品规格成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsStandard/?repage";
	}

}