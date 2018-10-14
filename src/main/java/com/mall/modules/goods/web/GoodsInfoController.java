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
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.service.GoodsInfoService;

/**
 * 商品信息Controller
 * @author hub
 * @version 2018-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/goods/goodsInfo")
public class GoodsInfoController extends BaseController {

	@Autowired
	private GoodsInfoService goodsInfoService;
	
	@ModelAttribute
	public GoodsInfo get(@RequestParam(required=false) String id) {
		GoodsInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsInfoService.get(id);
		}
		if (entity == null){
			entity = new GoodsInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("goods:goodsInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsInfo goodsInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsInfo> page = goodsInfoService.findPage(new Page<GoodsInfo>(request, response), goodsInfo); 
		model.addAttribute("page", page);
		return "modules/goods/goodsInfoList";
	}

	@RequiresPermissions("goods:goodsInfo:view")
	@RequestMapping(value = "form")
	public String form(GoodsInfo goodsInfo, Model model) {
		model.addAttribute("goodsInfo", goodsInfo);
		return "modules/goods/goodsInfoForm";
	}

	@RequiresPermissions("goods:goodsInfo:edit")
	@RequestMapping(value = "save")
	public String save(GoodsInfo goodsInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsInfo)){
			return form(goodsInfo, model);
		}
		goodsInfoService.save(goodsInfo);
		addMessage(redirectAttributes, "保存商品信息成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/?repage";
	}
	
	@RequiresPermissions("goods:goodsInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsInfo goodsInfo, RedirectAttributes redirectAttributes) {
		goodsInfoService.delete(goodsInfo);
		addMessage(redirectAttributes, "删除商品信息成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/?repage";
	}

}