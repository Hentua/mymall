package com.mall.modules.billboard.api;

import com.google.gson.JsonObject;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.Result;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.billboard.entity.IndexBillboard;
import com.mall.modules.billboard.service.IndexBillboardService;
import com.mall.modules.goods.entity.BillboardGoods;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.service.GoodsInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页广告位Controller
 * @author hub
 * @version 2018-10-14
 */
@Controller
@RequestMapping(value = "${adminPath}/api/billboard/")
public class IndexBillboardApi extends BaseController {

	@Autowired
	private IndexBillboardService indexBillboardService;

	@Autowired
	private GoodsInfoService goodsInfoService;

	/**
	 * 商品列表
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "getBillboards", method = RequestMethod.POST)
	public Result getBillboards(HttpServletRequest request, HttpServletResponse response) {
		//类型：1轮播图广告位 2独立广告位
//		String type = request.getParameter("type");
//		if(StringUtils.isEmpty(type)){
//			return ResultGenerator.genFailResult("参数[type]不能为空");
//		}
		//1轮播图广告位 2独立广告
		IndexBillboard billboard = new IndexBillboard();
		billboard.setType("1");
		List<IndexBillboard> list = indexBillboardService.findList(billboard);
		billboard.setType("2");
		List<IndexBillboard> list2 = indexBillboardService.findList(billboard);
		//独立广告位带商品集合
		for (IndexBillboard b: list2) {
				BillboardGoods billboardGoods = new BillboardGoods();
				billboardGoods.setBillboard(b);
				List<GoodsInfo> goodsInfos = goodsInfoService.findListByBillboard(billboardGoods);
				b.setGoodsList(goodsInfos);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("recommendData",list);
		map.put("adListData",list2);
		return ResultGenerator.genSuccessResult(map);
	}

}