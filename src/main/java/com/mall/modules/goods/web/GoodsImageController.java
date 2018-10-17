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
import com.mall.modules.goods.entity.GoodsImage;
import com.mall.modules.goods.service.GoodsImageService;

/**
 * 商品图片Controller
 * @author hub
 * @version 2018-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/goods/goodsImage")
public class GoodsImageController extends BaseController {

	@Autowired
	private GoodsImageService goodsImageService;
	
	@ModelAttribute
	public GoodsImage get(@RequestParam(required=false) String id) {
		GoodsImage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsImageService.get(id);
		}
		if (entity == null){
			entity = new GoodsImage();
		}
		return entity;
	}
	
	@RequiresPermissions("goods:goodsImage:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsImage goodsImage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsImage> page = goodsImageService.findPage(new Page<GoodsImage>(request, response), goodsImage); 
		model.addAttribute("page", page);
		return "modules/goods/goodsImageList";
	}

	@RequiresPermissions("goods:goodsImage:view")
	@RequestMapping(value = "form")
	public String form(GoodsImage goodsImage, Model model) {
		model.addAttribute("goodsImage", goodsImage);
		return "modules/goods/goodsImageForm";
	}

	@RequiresPermissions("goods:goodsImage:edit")
	@RequestMapping(value = "save")
	public String save(GoodsImage goodsImage, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsImage)){
			return form(goodsImage, model);
		}
		goodsImageService.save(goodsImage);
		addMessage(redirectAttributes, "保存商品图片成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsImage/?repage";
	}
	
	@RequiresPermissions("goods:goodsImage:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsImage goodsImage, RedirectAttributes redirectAttributes) {
		goodsImageService.delete(goodsImage);
		addMessage(redirectAttributes, "删除商品图片成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsImage/?repage";
	}

}