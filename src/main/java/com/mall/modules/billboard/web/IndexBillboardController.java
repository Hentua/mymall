package com.mall.modules.billboard.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.goods.entity.BillboardGoods;
import com.mall.modules.goods.entity.GoodsCategory;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.service.GoodsCategoryService;
import com.mall.modules.goods.service.GoodsInfoService;
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
import com.mall.modules.billboard.entity.IndexBillboard;
import com.mall.modules.billboard.service.IndexBillboardService;

import java.util.List;

/**
 * 首页广告位Controller
 * @author hub
 * @version 2018-10-14
 */
@Controller
@RequestMapping(value = "${adminPath}/billboard/indexBillboard")
public class IndexBillboardController extends BaseController {

	@Autowired
	private IndexBillboardService indexBillboardService;

	@Autowired
	private GoodsInfoService goodsInfoService;

	@Autowired
	private GoodsCategoryService goodsCategoryService;

	@ModelAttribute
	public IndexBillboard get(@RequestParam(required=false) String id) {
		IndexBillboard entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = indexBillboardService.get(id);
		}
		if (entity == null){
			entity = new IndexBillboard();
		}
		return entity;
	}
	
	@RequiresPermissions("billboard:indexBillboard:view")
	@RequestMapping(value = {"list", ""})
	public String list(IndexBillboard indexBillboard, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IndexBillboard> page = indexBillboardService.findPage(new Page<IndexBillboard>(request, response), indexBillboard); 
		model.addAttribute("page", page);
		return "modules/billboard/indexBillboardList";
	}

	@RequiresPermissions("billboard:indexBillboard:view")
	@RequestMapping(value = "form")
	public String form(IndexBillboard indexBillboard, Model model) {
		if(!StringUtils.isEmpty(indexBillboard.getId())){
			BillboardGoods billboardGoods = new BillboardGoods();
			billboardGoods.setBillboard(indexBillboard);
			List<GoodsInfo> goodsInfos = goodsInfoService.findListByBillboard(billboardGoods);
			indexBillboard.setGoodsList(goodsInfos);
		}
		GoodsCategory gc = new GoodsCategory();
		gc.setDepth(1);
		List<GoodsCategory> categories = goodsCategoryService.findList(gc);
		model.addAttribute("categories",categories);
		model.addAttribute("indexBillboard", indexBillboard);
		return "modules/billboard/indexBillboardForm";
	}

	@RequiresPermissions("billboard:indexBillboard:edit")
	@RequestMapping(value = "save")
	public String save(IndexBillboard indexBillboard, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, indexBillboard)){
			return form(indexBillboard, model);
		}
		if("5".equals(indexBillboard.getType())){
			indexBillboard.setJumpId(indexBillboard.getCategoryId());
		}
		indexBillboardService.save(indexBillboard);
		addMessage(redirectAttributes, "保存首页广告位成功");
		return "redirect:"+Global.getAdminPath()+"/billboard/indexBillboard/?repage";
	}
	
	@RequiresPermissions("billboard:indexBillboard:edit")
	@RequestMapping(value = "delete")
	public String delete(IndexBillboard indexBillboard, RedirectAttributes redirectAttributes) {
		indexBillboardService.delete(indexBillboard);
		addMessage(redirectAttributes, "删除首页广告位成功");
		return "redirect:"+Global.getAdminPath()+"/billboard/indexBillboard/?repage";
	}

}