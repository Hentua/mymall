package com.mall.modules.goods.api;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.service.ServiceException;
import com.mall.common.utils.Result;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.TreeNode;
import com.mall.common.web.BaseController;
import com.mall.modules.goods.entity.*;
import com.mall.modules.goods.service.*;
import com.mall.modules.member.entity.MemberFavorite;
import com.mall.modules.member.entity.MemberFootprint;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.entity.UserVo;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/api/goods/")
public class GoodsInfoApi extends BaseController {


    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private GoodsImageService goodsImageService;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private GoodsStandardService goodsStandardService;

    @Autowired
    private GoodsEvaluateService goodsEvaluateService;

    @Autowired
    private GoodsRecommendService goodsRecommendService;


    /**
     * 商品商家关键字搜索
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "merchantQuery", method = RequestMethod.POST)
    public void goodsMerchantInfoQuery(HttpServletRequest request, HttpServletResponse response) {
        String keyWord = request.getParameter("keyWord");
        if(StringUtils.isEmpty(keyWord)){
            renderString(response,ResultGenerator.genSuccessResult(new Object()));
        }
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setNickname(request.getParameter("keyWord"));
        memberInfo.setStatus("1");
        Page<MemberInfo> page = memberInfoService.findPage(new Page<MemberInfo>(request, response), memberInfo);
        renderString(response,ResultGenerator.genSuccessResult(page));
    }




    /**
     * 商品列表
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "goodsList", method = RequestMethod.POST)
    public void goodsList(HttpServletRequest request, HttpServletResponse response) {
        String sortType = request.getParameter("sortType");
        String sortWay = request.getParameter("sortWay");
        String keyWord = request.getParameter("keyWord");
        String adId = request.getParameter("adId");

        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setGoodsCategoryId(request.getParameter("goodsCategoryId"));
        if(!StringUtils.isEmpty(keyWord)){
            goodsInfo.setKeyWord(keyWord);
        }
        goodsInfo.setMerchantId(request.getParameter("merchantId"));
        goodsInfo.setAdId(adId);
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
        renderString(response,ResultGenerator.genSuccessResult(page));
    }

    /**
     * 商品详情
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "goodsDetails", method = RequestMethod.POST)
    public void goodsDetails(HttpServletRequest request, HttpServletResponse response) {
        GoodsInfo goodsInfo = null;
        String goodsRecommendCode = request.getParameter("goodsRecommendCode");
        if(!StringUtils.isEmpty(goodsRecommendCode)){
            GoodsRecommend goodsRecommend= goodsRecommendService.get(goodsRecommendCode);
            if(null == goodsRecommend){
                throw new ServiceException("无效推荐码");
            }
            goodsInfo=goodsInfoService.get(goodsRecommend.getGoodsId());
        }else{
            goodsInfo=goodsInfoService.get(request.getParameter("goodsId"));
        }

        if(null == goodsInfo || goodsInfo.getStatus() != 2){
            renderString(response,ResultGenerator.genFailResult("商品已下架").setData("商品已下架"));
        }
        MemberInfo m = new MemberInfo();
        m.setId(goodsInfo.getMerchantId());
        m = memberInfoService.get(m);
        List<GoodsImage> goodsImages = goodsImageService.findListByGoodsId(goodsInfo.getId());
        String goodsImagesStr = "";
        if(null != goodsImages && goodsImages.size()!=0){
            goodsImagesStr = goodsImages.get(0).getImageUrl();
        }
        String[] goodsImageArray = goodsImagesStr.split("\\|");
        goodsImages = new ArrayList<>();
        for (String img: goodsImageArray) {
            if(StringUtils.isEmpty(img)){
                continue;
            }
            GoodsImage goodsImage = new GoodsImage();
            goodsImage.setImageUrl(img);
            goodsImage.setGoodsId(goodsInfo.getId());
            goodsImages.add(goodsImage);
        }
        goodsInfo.setGoodsImages(goodsImages);

        GoodsStandard goodsStandard = new GoodsStandard();
        goodsStandard.setGoodsId(goodsInfo.getId());
        List<GoodsStandard> goodsStandards = goodsStandardService.findList(goodsStandard);
        goodsInfo.setGoodsStandards(goodsStandards);


        // 获取当前登录用户
        User currUser = UserUtils.getUser();
        // 判断是否登录
        if(null != currUser && StringUtils.isNotBlank(currUser.getId())) {

            //获取当前用户 此商品是否收藏
            MemberFavorite queryCondition = new MemberFavorite();
            queryCondition.setCustomerCode(currUser.getId());
            queryCondition.setGoodsId(goodsInfo.getId());
            List<MemberFavorite> memberFavorites = memberInfoService.findList(queryCondition);
            if(memberFavorites != null && memberFavorites.size()>0){
                goodsInfo.setFavorite("1");
                goodsInfo.setFavoriteId(memberFavorites.get(0).getId());
            }
            // 用户已登录，保存用户足迹
            MemberFootprint memberFootprint = new MemberFootprint();
            memberFootprint.setCustomerCode(currUser.getId());
            memberFootprint.setGoodsId(goodsInfo.getId());
            memberInfoService.addFootprint(memberFootprint);
        }
        //商品评价
        GoodsEvaluate goodsEvaluate =new GoodsEvaluate();
        goodsEvaluate.setGoodsId(goodsInfo.getId());
        Map<String,Object> map = goodsEvaluateService.findCount(goodsEvaluate);
        List<GoodsEvaluate> goodsEvaluates = goodsEvaluateService.findListBy2(goodsEvaluate);
        JSONObject evaluate = new JSONObject();
        evaluate.putAll(map);
        evaluate.put("list",goodsEvaluates);

        JSONObject recommend = new JSONObject();
        recommend.put("goodsRecommendCode",goodsRecommendCode);
        JSONObject result = new JSONObject();
        JSONObject merchant = new JSONObject();
        merchant.put("id",m.getId());
        merchant.put("photo",Global.getConfig("userfiles.baseURL") +m.getAvatar());
        merchant.put("avatar", Global.getConfig("userfiles.baseURL") +m.getAvatar());
        merchant.put("nickname",m.getNickname());
        merchant.putAll(goodsInfoService.monthSalesTotal(m.getId()));
        result.put("goodsInfo",goodsInfo);
        result.put("merchant",merchant);
        result.put("evaluate",evaluate);
        result.put("recommend",recommend);
        renderString(response,ResultGenerator.genSuccessResult(result));
    }

    /**
     * 商品分类
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "goodsCategory", method = RequestMethod.POST)
    public void goodsCategory(HttpServletRequest request, HttpServletResponse response) {
        List<GoodsCategory> list = goodsCategoryService.findListByApi(new GoodsCategory());

        TreeNode<GoodsCategory> tree=new TreeNode<GoodsCategory>(list);
        renderString(response,ResultGenerator.genSuccessResult(tree.TreeFormat()));
    }


    @ResponseBody
    @RequestMapping(value = "addGoodsEvaluate", method = RequestMethod.POST)
    public void addGoodsEvaluate(HttpServletRequest request, HttpServletResponse response) {
        GoodsInfo goodsInfo = goodsInfoService.get(request.getParameter("goodsId"));
        if(null == goodsInfo){
            renderString(response,ResultGenerator.genFailResult("商品已下架"));
        }
        User user = UserUtils.getUser();
        GoodsEvaluate goodsEvaluate = new GoodsEvaluate();
        goodsEvaluate.setGoodsId(request.getParameter("goodsId"));
        goodsEvaluate.setEvaluate(request.getParameter("evaluate"));
        goodsEvaluate.setLevel(request.getParameter("level"));
        goodsEvaluate.setTitle(request.getParameter("title"));
        goodsEvaluate.setGoodsName(goodsInfo.getGoodsName());
        goodsEvaluate.setEvaluateUserId(user.getId());
        goodsEvaluate.setEvaluateUserName(user.getNickname());
        goodsEvaluate.setEvaluateDate(new Date());
        goodsEvaluateService.save(goodsEvaluate);
        renderString(response, ResultGenerator.genSuccessResult("成功"));
    }


    @ResponseBody
    @RequestMapping(value = "goodsEvaluateList", method = RequestMethod.POST)
    public void goodsEvaluateList(HttpServletRequest request, HttpServletResponse response) {
        GoodsInfo goodsInfo = goodsInfoService.get(request.getParameter("goodsId"));
        if(null == goodsInfo){
            renderString(response,  ResultGenerator.genFailResult("商品信息未找到"));
        }
        logger.info(request.getParameter("pageNo"));
        logger.info(request.getParameter("pageSize"));
        GoodsEvaluate goodsEvaluate = new GoodsEvaluate();
        goodsEvaluate.setGoodsId(goodsInfo.getId());
        Page<GoodsEvaluate> page = new Page<GoodsEvaluate>(request,response);
        page = goodsEvaluateService.findPage(page,goodsEvaluate);
        renderString(response, ResultGenerator.genSuccessResult(page));
    }
}
