package com.mall.modules.goods.api;

import com.alibaba.fastjson.JSONObject;
import com.mall.common.persistence.Page;
import com.mall.common.utils.Result;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.TreeNode;
import com.mall.common.web.BaseController;
import com.mall.modules.goods.entity.*;
import com.mall.modules.goods.service.*;
import com.mall.modules.member.entity.MemberFavorite;
import com.mall.modules.member.entity.MemberFootprint;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public Result goodsRecommend(HttpServletRequest request, HttpServletResponse response) {
        User user = UserUtils.getUser();
        GoodsRecommend goodsRecommend = new GoodsRecommend();
        goodsRecommend.setGoodsId(request.getParameter("goodsId"));
        goodsRecommend.setUserId(user.getId());
        goodsRecommendService.save(goodsRecommend);
        JSONObject jo =new JSONObject();
        jo.put("goodsRecommendCode",goodsRecommend.getId());
        return ResultGenerator.genSuccessResult(jo);
    }
}
