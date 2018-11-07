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
import com.mall.modules.goods.entity.GoodsRecommend;
import com.mall.modules.goods.service.GoodsRecommendService;

/**
 * 商品推荐Controller
 * @author hub
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/goods/goodsRecommend")
public class GoodsRecommendController extends BaseController {

	@Autowired
	private GoodsRecommendService goodsRecommendService;
	
	@ModelAttribute
	public GoodsRecommend get(@RequestParam(required=false) String id) {
		GoodsRecommend entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsRecommendService.get(id);
		}
		if (entity == null){
			entity = new GoodsRecommend();
		}
		return entity;
	}
	
	@RequiresPermissions("goods:goodsRecommend:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsRecommend goodsRecommend, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsRecommend> page = goodsRecommendService.findPage(new Page<GoodsRecommend>(request, response), goodsRecommend); 
		model.addAttribute("page", page);
		return "modules/goods/goodsRecommendList";
	}

	@RequiresPermissions("goods:goodsRecommend:view")
	@RequestMapping(value = "form")
	public String form(GoodsRecommend goodsRecommend, Model model) {
		model.addAttribute("goodsRecommend", goodsRecommend);
		return "modules/goods/goodsRecommendForm";
	}

	@RequiresPermissions("goods:goodsRecommend:edit")
	@RequestMapping(value = "save")
	public String save(GoodsRecommend goodsRecommend, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsRecommend)){
			return form(goodsRecommend, model);
		}
		goodsRecommendService.save(goodsRecommend);
		addMessage(redirectAttributes, "保存商品推荐成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsRecommend/?repage";
	}
	
	@RequiresPermissions("goods:goodsRecommend:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsRecommend goodsRecommend, RedirectAttributes redirectAttributes) {
		goodsRecommendService.delete(goodsRecommend);
		addMessage(redirectAttributes, "删除商品推荐成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsRecommend/?repage";
	}

}