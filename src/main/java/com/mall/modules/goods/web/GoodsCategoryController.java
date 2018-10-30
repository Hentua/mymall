package com.mall.modules.goods.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mall.modules.sys.entity.Office;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.web.BaseController;
import com.mall.common.utils.StringUtils;
import com.mall.modules.goods.entity.GoodsCategory;
import com.mall.modules.goods.service.GoodsCategoryService;

import java.util.List;
import java.util.Map;

/**
 * 商品分类Controller
 * @author hub
 * @version 2018-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/goods/goodsCategory")
public class GoodsCategoryController extends BaseController {

	@Autowired
	private GoodsCategoryService goodsCategoryService;
	
	@ModelAttribute
	public GoodsCategory get(@RequestParam(required=false) String id) {
		GoodsCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = goodsCategoryService.get(id);
		}
		if (entity == null){
			entity = new GoodsCategory();
		}
		return entity;
	}

	@RequiresPermissions("goods:goodsCategory:view")
	@RequestMapping(value = { ""})
	public String index(GoodsCategory goodsCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/goods/goodsCategoryIndex";
	}
	
	@RequiresPermissions("goods:goodsCategory:view")
	@RequestMapping(value = {"list"})
	public String list(GoodsCategory goodsCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
//		if(StringUtils.isEmpty(goodsCategory.getParentCategoryId())
//			&& null == goodsCategory.getStatus()){
//			goodsCategory.setParentCategoryId("0");
//		}
		Page<GoodsCategory> page = goodsCategoryService.findPage(new Page<GoodsCategory>(request, response), goodsCategory);
		model.addAttribute("page", page);
		return "modules/goods/goodsCategoryList";
	}

	@RequiresPermissions("goods:goodsCategory:view")
	@RequestMapping(value = "form")
	public String form(GoodsCategory goodsCategory, Model model) {
		model.addAttribute("goodsCategory", goodsCategory);
		return "modules/goods/goodsCategoryForm";
	}

	@RequiresPermissions("goods:goodsCategory:edit")
	@RequestMapping(value = "save")
	public String save(GoodsCategory goodsCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsCategory)){
			return form(goodsCategory, model);
		}
		GoodsCategory pg = goodsCategoryService.get(goodsCategory.getParentCategoryId());
		if(null == pg){
			goodsCategory.setParentCategoryId("0");
			goodsCategory.setDepth(0);
		}else{
			goodsCategory.setDepth(pg.getDepth()+1);
		}
		goodsCategory.setStatus(1);
		goodsCategoryService.save(goodsCategory);
		addMessage(redirectAttributes, "保存商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsCategory/list?repage";
	}
	
	@RequiresPermissions("goods:goodsCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsCategory goodsCategory, RedirectAttributes redirectAttributes) {
		goodsCategoryService.delete(goodsCategory);
		addMessage(redirectAttributes, "删除商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsCategory/list?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type
			, @RequestParam(required=false) String parentCategoryId,
											  @RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		GoodsCategory gc = new GoodsCategory();
		if(!StringUtils.isEmpty(parentCategoryId)){
			gc.setParentCategoryId(parentCategoryId);
		}
		List<GoodsCategory> list = goodsCategoryService.findList(gc);
		for (int i=0; i<list.size(); i++){
			GoodsCategory e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) )) ){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentCategoryId());
//				map.put("pIds", e.getParentIds());
				map.put("name", e.getCategoryName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}

}