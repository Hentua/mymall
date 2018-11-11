package com.mall.modules.goods.web;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.goods.entity.GoodsImage;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.entity.GoodsStandard;
import com.mall.modules.goods.service.GoodsImageService;
import com.mall.modules.goods.service.GoodsInfoService;
import com.mall.modules.goods.service.GoodsStandardService;
import com.mall.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	@Autowired
	private GoodsStandardService goodsStandardService;
	
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
		goodsInfo.setMerchantId(UserUtils.getUser().getId());
		goodsInfo.setGoodsType(1);
		Page<GoodsInfo> page = goodsInfoService.findPage(new Page<GoodsInfo>(request, response), goodsInfo); 
		model.addAttribute("page", page);
		return "modules/goods/goodsInfoList";
	}

	@RequiresPermissions("goods:goodsInfo:view")
	@RequestMapping(value = {"platList", ""})
	public String platList(GoodsInfo goodsInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
//		goodsInfo.setMerchantId(UserUtils.getUser().getId());
		goodsInfo.setGoodsType(2);
		Page<GoodsInfo> page = goodsInfoService.findPage(new Page<GoodsInfo>(request, response), goodsInfo);
		model.addAttribute("page", page);
		return "modules/goods/platGoodsInfoList";
	}

	@RequiresPermissions("goods:goodsInfo:view")
	@RequestMapping(value = {"selectList"})
	public String selectList(GoodsInfo goodsInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		goodsInfo.setStatus(3);
		Page<GoodsInfo> page = goodsInfoService.findSelectList(new Page<GoodsInfo>(request, response, 10), goodsInfo);
		model.addAttribute("page", page);
		return "modules/goods/goodsSelectList";
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
			//商品规格
			GoodsStandard goodsStandard = new GoodsStandard();
			goodsStandard.setGoodsId(goodsInfo.getId());
			List<GoodsStandard> goodsStandards = goodsStandardService.findList(goodsStandard);
			goodsInfo.setGoodsStandards(goodsStandards);
		}
		goodsInfo.setGoodsType(1);
		model.addAttribute("goodsInfo", goodsInfo);
		return "modules/goods/goodsInfoForm";
	}

	@RequiresPermissions("goods:goodsInfo:view")
	@RequestMapping(value = "platForm")
	public String platForm(GoodsInfo goodsInfo, Model model) {
//		goodsInfo.getId()
		if(!StringUtils.isEmpty(goodsInfo.getId())){
			goodsInfo = get(goodsInfo.getId());
			List<GoodsImage> gms = goodsImageService.findListByGoodsId(goodsInfo.getId());
			List<String> images = new ArrayList<String>();
			for (GoodsImage m: gms) {
				images.add(m.getImageUrl());
			}
			goodsInfo.setDespImages(images);
			//商品规格
			GoodsStandard goodsStandard = new GoodsStandard();
			goodsStandard.setGoodsId(goodsInfo.getId());
			List<GoodsStandard> goodsStandards = goodsStandardService.findList(goodsStandard);
			goodsInfo.setGoodsStandards(goodsStandards);
		}
		goodsInfo.setGoodsType(2);
		model.addAttribute("goodsInfo", goodsInfo);
		return "modules/goods/platGoodsInfoForm";
	}

	@RequiresPermissions("goods:goodsInfo:view")
	@RequestMapping(value = "goodsDetailCheck")
	public String goodsDetailCheck(GoodsInfo goodsInfo, Model model) {

//		goodsInfo.getId()
		if(!StringUtils.isEmpty(goodsInfo.getId())){
			goodsInfo = get(goodsInfo.getId());
			List<GoodsImage> gms = goodsImageService.findListByGoodsId(goodsInfo.getId());
			List<String> images = new ArrayList<String>();
			for (GoodsImage m: gms) {
				images.add(m.getImageUrl());
			}
			goodsInfo.setDespImages(images);
			//商品规格
			GoodsStandard goodsStandard = new GoodsStandard();
			goodsStandard.setGoodsId(goodsInfo.getId());
			List<GoodsStandard> goodsStandards = goodsStandardService.findList(goodsStandard);
			goodsInfo.setGoodsStandards(goodsStandards);
		}
		model.addAttribute("goodsInfo", goodsInfo);
		return "modules/goods/goodsDetailCheck";
	}

	@RequiresPermissions("goods:goodsInfo:view")
	@RequestMapping(value = "goodsDetail")
	public String goodsDetail(GoodsInfo goodsInfo, Model model) {

//		goodsInfo.getId()
		if(!StringUtils.isEmpty(goodsInfo.getId())){
			goodsInfo = get(goodsInfo.getId());
			List<GoodsImage> gms = goodsImageService.findListByGoodsId(goodsInfo.getId());
			List<String> images = new ArrayList<String>();
			for (GoodsImage m: gms) {
				images.add(m.getImageUrl());
			}
			goodsInfo.setDespImages(images);
			//商品规格
			GoodsStandard goodsStandard = new GoodsStandard();
			goodsStandard.setGoodsId(goodsInfo.getId());
			List<GoodsStandard> goodsStandards = goodsStandardService.findList(goodsStandard);
			goodsInfo.setGoodsStandards(goodsStandards);
		}
		model.addAttribute("goodsInfo", goodsInfo);
		return "modules/goods/goodsDetail";
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
		if(2 == goodsInfo.getGoodsType()){
			return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/platList?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/list?repage";
		}


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
			//上架更新结算金额
			GoodsStandard goodsStandard = new GoodsStandard();
			goodsStandard.setGoodsId(goodsInfo.getId());
			List<GoodsStandard> goodsStandards = goodsStandardService.findList(goodsStandard);
			for(int i=0;i<goodsStandards.size();i++){
				goodsStandards.get(i).setSettlementsAmount(goodsInfo.getSettlementsAmounts()[i]);
				goodsStandardService.save(goodsStandards.get(i));
			}
		}
		if(0 == goodsInfo.getStatus()){
			g.setOnlinetime(null);
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
		}else{
			goodsInfo.setSalesTotal(0);
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
		//商品规格
		if(null != goodsInfo.getGoodsStandardsName() && goodsInfo.getGoodsStandardsName().length>0){
			goodsStandardService.deleteByGoodsId(goodsInfo.getId());
			for(int i=0;i<goodsInfo.getGoodsStandardsName().length;i++){
				if(null == goodsInfo.getGoodsPrice() || 0 == goodsInfo.getGoodsPrice()){
					goodsInfo.setGoodsPrice(goodsInfo.getGoodsStandardsPrice()[i]);
				}
				GoodsStandard goodsStandard =new GoodsStandard();
				goodsStandard.setName(goodsInfo.getGoodsStandardsName()[i]);
				goodsStandard.setPrice(goodsInfo.getGoodsStandardsPrice()[i]);
				goodsStandard.setGoodsId(goodsInfo.getId());
				goodsStandard.setCategoryId(goodsInfo.getGoodsCategoryId());
				goodsStandardService.save(goodsStandard);
			 }
		}

		goodsInfoService.save(goodsInfo);

		addMessage(redirectAttributes, "保存商品信息成功");
		if(2 == goodsInfo.getGoodsType()){
			return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/platList/?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/list/?repage";
		}

	}


	@RequiresPermissions("goods:goodsInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(GoodsInfo goodsInfo, RedirectAttributes redirectAttributes) {
		goodsInfoService.delete(goodsInfo);
		addMessage(redirectAttributes, "删除商品信息成功");
		if(2 == goodsInfo.getGoodsType()){
			return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/platList?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/goods/goodsInfo/list?repage";
		}

	}

}