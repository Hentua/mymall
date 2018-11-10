package com.mall.modules.billboard.api;

import com.mall.common.utils.Result;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.web.BaseController;
import com.mall.modules.billboard.entity.IndexBillboard;
import com.mall.modules.billboard.service.IndexBillboardService;
import com.mall.modules.goods.entity.BillboardGoods;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	 * 首页广告
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
		map.put("adListData",list);
		map.put("recommendData",list2);
		return ResultGenerator.genSuccessResult(map);
	}


	/**
	 * 开机广告
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "getOpenBillboards", method = RequestMethod.POST)
	public Result getOpenBillboards(HttpServletRequest request, HttpServletResponse response) {
		//1轮播图广告位 2独立广告
		IndexBillboard billboard = new IndexBillboard();
		billboard.setType("3");
		List<IndexBillboard> list = indexBillboardService.findList(billboard);
		return ResultGenerator.genSuccessResult(list);
	}

}