package com.mall.modules.goods.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.goods.entity.GoodsImage;
import com.mall.modules.goods.service.GoodsImageService;
import com.mall.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	@Autowired
	private GoodsImageService goodsImageService;
	
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
	@RequestMapping(value = {"checkList", ""})
	public String checkList(GoodsInfo goodsInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GoodsInfo> page = goodsInfoService.findPage(new Page<GoodsInfo>(request, response), goodsInfo);
		model.addAttribute("page", page);
		return "modules/goods/goodsInfoCheckList";
	}

	@RequiresPermissions("goods:goodsInfo:view")
	@RequestMapping(value = "form")
	public String form(GoodsInfo goodsInfo, Model model) {

//		goodsInfo.getId()
		if(!StringUtils.isEmpty(goodsInfo.getId())){
			goodsInfo = get(goodsInfo.getId());
			List<GoodsImage> gms = goodsImageService.findListByGoodsId(goodsInfo.getId());
			List<String> images = new ArrayList<String>();
			for (GoodsImage m: gms) {
				images.add(m.getImageUrl());
			}
			goodsInfo.setDespImages(images);
		}
		model.addAttribute("goodsInfo", goodsInfo);
		return "modules/goods/goodsInfoForm";
	}


	@RequiresPermissions("goods:goodsInfo:edit")
	@RequestMapping(value = "updateStatus")
	public String updateStatus(GoodsInfo goodsInfo, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, goodsInfo)){
//			return form(goodsInfo, model);
//		}
		GoodsInfo g = goodsInfoService.get(goodsInfo.getId());
		if(3 == goodsInfo.getStatus()) {
			g.setOnlinetime(new Date());
		}
		g.setStatus(goodsInfo.getStatus());
		goodsInfoService.save(g);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/list?repage";
	}

	@RequiresPermissions("goods:goodsInfo:edit")
	@RequestMapping(value = "updateStatusCheck")
	public String updateStatusCheck(GoodsInfo goodsInfo, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, goodsInfo)){
//			return form(goodsInfo, model);
//		}
		GoodsInfo g = goodsInfoService.get(goodsInfo.getId());
		if(3 == goodsInfo.getStatus()) {
			g.setOnlinetime(new Date());
		}
		g.setStatus(goodsInfo.getStatus());
		goodsInfoService.save(g);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/checkList?repage";
	}


	@RequiresPermissions("goods:goodsInfo:edit")
	@RequestMapping(value = "save")
	public String save(GoodsInfo goodsInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, goodsInfo)){
			return form(goodsInfo, model);
		}
		if(!StringUtils.isEmpty(goodsInfo.getId())){
			GoodsImage gm = new GoodsImage();
			gm.setGoodsId(goodsInfo.getId());
			//清空图片
			goodsImageService.deleteByGoodsId(gm);
		}
		goodsInfo.setMerchantId(UserUtils.getUser().getId());
		goodsInfo.setStatus(1);
		goodsInfoService.save(goodsInfo);
		if(null != goodsInfo.getDespImages() && goodsInfo.getDespImages().size()>0){
			for (String image : goodsInfo.getDespImages() ) {
				GoodsImage goodsImage = new GoodsImage();
				goodsImage.setGoodsId(goodsInfo.getId());
				goodsImage.setImageUrl(image);
				goodsImageService.save(goodsImage);
			}
		}
		addMessage(redirectAttributes, "保存商品信息成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/list/?repage";
	}


	@RequiresPermissions("goods:goodsInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsInfo goodsInfo, RedirectAttributes redirectAttributes) {
		goodsInfoService.delete(goodsInfo);
		addMessage(redirectAttributes, "删除商品信息成功");
		return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/list?repage";
	}

}