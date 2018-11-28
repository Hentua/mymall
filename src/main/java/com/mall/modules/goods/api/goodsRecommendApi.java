package com.mall.modules.goods.api;

import com.alibaba.fastjson.JSONObject;
import com.mall.common.utils.Result;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.web.BaseController;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.entity.GoodsRecommend;
import com.mall.modules.goods.service.*;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/api/recommend/")
public class goodsRecommendApi extends BaseController {


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
     * 商品推荐
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "goodsRecommend", method = RequestMethod.POST)
    public void goodsRecommend(HttpServletRequest request, HttpServletResponse response)throws Exception {
        User user = UserUtils.getUser();
        String goodsId = request.getParameter("goodsId");
        GoodsInfo goodsInfo = goodsInfoService.get(goodsId);
        if(goodsInfo == null){
            throw new ServletException("无效商品信息");
        }
        GoodsRecommend goodsRecommend = new GoodsRecommend();
        goodsRecommend.setGoodsId(goodsInfo.getId());
        goodsRecommend.setUserId(user.getId());
        goodsRecommendService.save(goodsRecommend);
        JSONObject jo =new JSONObject();
        String code = goodsInfo.getGoodsName()+",复制整段信息，打开美易优选,（"+goodsRecommend.getId()+"）";
        jo.put("goodsRecommendCode",code);
        renderString(response,ResultGenerator.genSuccessResult(jo));
    }
}
