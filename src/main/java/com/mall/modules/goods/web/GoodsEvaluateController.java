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
import com.mall.modules.goods.entity.GoodsEvaluate;
import com.mall.modules.goods.service.GoodsEvaluateService;

/**
 * 商品评价Controller
 * @author hub
 * @version 2018-11-06
 */
@Controller
@RequestMapping(value = "${adminPath}/goods/goodsEvaluate")
public class GoodsEvaluateController extends BaseController {

	@Autowired
	private GoodsEvaluateService goodsEvaluateService;
	
	@ModelAttribute
	public GoodsEvaluate get(@RequestParam(required=false) String id) {
		GoodsEvaluate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsEvaluateService.get(id);
		}
		if (entity == null){
			entity = new GoodsEvaluate();
		}
		return entity;
	}
	
	@RequiresPermissions("goods:goodsEvaluate:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsEvaluate goodsEvaluate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsEvaluate> page = goodsEvaluateService.findPage(new Page<GoodsEvaluate>(request, response), goodsEvaluate); 
		model.addAttribute("page", page);
		return "modules/goods/goodsEvaluateList";
	}

	@RequiresPermissions("goods:goodsEvaluate:view")
	@RequestMapping(value = "form")
	public String form(GoodsEvaluate goodsEvaluate, Model model) {
		model.addAttribute("goodsEvaluate", goodsEvaluate);
		return "modules/goods/goodsEvaluateForm";
	}

	@RequiresPermissions("goods:goodsEvaluate:edit")
	@RequestMapping(value = "save")
	public String save(GoodsEvaluate goodsEvaluate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsEvaluate)){
			return form(goodsEvaluate, model);
		}
		goodsEvaluateService.save(goodsEvaluate);
		addMessage(redirectAttributes, "保存商品评价成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsEvaluate/?repage";
	}
	
	@RequiresPermissions("goods:goodsEvaluate:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsEvaluate goodsEvaluate, RedirectAttributes redirectAttributes) {
		goodsEvaluateService.delete(goodsEvaluate);
		addMessage(redirectAttributes, "删除商品评价成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsEvaluate/?repage";
	}

}