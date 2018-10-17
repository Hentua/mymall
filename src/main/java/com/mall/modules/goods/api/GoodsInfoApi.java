package com.mall.modules.goods.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mall.common.persistence.Page;
import com.mall.common.utils.Result;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.TreeNode;
import com.mall.common.web.BaseController;
import com.mall.modules.goods.entity.GoodsCategory;
import com.mall.modules.goods.entity.GoodsImage;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.service.GoodsCategoryService;
import com.mall.modules.goods.service.GoodsImageService;
import com.mall.modules.goods.service.GoodsInfoService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.entity.UserVo;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/api/goods/")
public class GoodsInfoApi extends BaseController {


    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private GoodsImageService goodsImageService;


    /**
     * 商品列表
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "goodsList", method = RequestMethod.POST)
    public Result goodsList(HttpServletRequest request, HttpServletResponse response) {
        String sortType = request.getParameter("sortType");
        String sortWay = request.getParameter("sortWay");
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setGoodsCategoryId(request.getParameter("goodsCategoryId"));
        goodsInfo.setGoodsName(request.getParameter("goodsName"));
        Page<GoodsInfo> page = new Page<GoodsInfo>(request,response);
        //排序类型 1综合排序 2销量排序 3价格排序
        if(StringUtils.isEmpty(sortType) || "1".equals(sortType)){

        }else if("2".equals(sortType)){
            page.setOrderBy("sales_total");
        }else if("3".equals(sortType)){
            page.setOrderBy("goods_price");
        }
        //排序方式 1升序 2降序
        if(StringUtils.isEmpty(sortWay) || "1".equals(sortWay)){
            page.setSortWay("asc");
        }else if("2".equals(sortWay)){
            page.setSortWay("desc");
        }


        page = goodsInfoService.findPageByApi(page,goodsInfo);
        return ResultGenerator.genSuccessResult(page);
    }

    /**
     * 商品详情
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "goodsDetails", method = RequestMethod.POST)
    public Result goodsDetails(HttpServletRequest request, HttpServletResponse response) {
        GoodsInfo goodsInfo = goodsInfoService.get(request.getParameter("goodsId"));
        User merchant = UserUtils.get(goodsInfo.getMerchantId());
        List<GoodsImage> goodsImages = goodsImageService.findListByGoodsId(goodsInfo.getId());
        goodsInfo.setGoodsImages(goodsImages);
        JSONObject result = new JSONObject();
        result.put("goodsInfo",goodsInfo);
        result.put("merchant",new UserVo(merchant));
        return ResultGenerator.genSuccessResult(result);
    }

    /**
     * 商品分类
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "goodsCategory", method = RequestMethod.POST)
    public Result goodsCategory(HttpServletRequest request, HttpServletResponse response) {
        List<GoodsCategory> list = goodsCategoryService.findList(new GoodsCategory());
        TreeNode<GoodsCategory> tree=new TreeNode<GoodsCategory>(list);
        return ResultGenerator.genSuccessResult(tree.TreeFormat());
    }
}
