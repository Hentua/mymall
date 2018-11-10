package com.mall.modules.order.api;

import com.mall.common.service.ServiceException;
import com.mall.common.utils.api.ApiExceptionHandleUtil;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.goods.entity.GoodsStandard;
import com.mall.modules.goods.service.GoodsStandardService;
import com.mall.modules.order.entity.OrderShoppingCart;
import com.mall.modules.order.entity.OrderShoppingCartVo;
import com.mall.modules.order.service.OrderShoppingCartService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车API
 *
 * @author wankang
 * @since 2018-10-16
 */
@RestController
@RequestMapping(value = "${adminPath}/api")
public class OrderShoppingCartApi extends BaseController {

    @Autowired
    private OrderShoppingCartService orderShoppingCartService;

    @Autowired
    private GoodsStandardService goodsStandardService;

    /**
     * 添加商品到购物车
     *
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "addShoppingCart", method = RequestMethod.POST)
    public void addShoppingCart(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String goodsId = request.getParameter("goodsId");
        String merchantCode = request.getParameter("merchantCode");

//        String goodsPrice = request.getParameter("goodsPrice");
//        String goodsStandard = request.getParameter("goodsStandard");
//        String settlementsAmount = request.getParameter("settlementsAmount");
        String goodsStandardId =request.getParameter("goodsStandardId");//商品规格ID
        String goodsRecommendCode =request.getParameter("goodsRecommendCode");//商品推荐码
        GoodsStandard goodsStandard = goodsStandardService.get(goodsStandardId);
        try {
            if(null == currUser) {
                throw new ServiceException("未登录");
            }
            if(StringUtils.isBlank(goodsId)) {
                throw new ServiceException("未选择商品");
            }
            if(StringUtils.isBlank(merchantCode)) {
                throw new ServiceException("商家信息错误");
            }
            if(null == goodsStandard){
                throw new ServiceException("未选择商品规格");
            }
            OrderShoppingCart condition = new OrderShoppingCart();
            condition.setGoodsId(goodsId);
            condition.setCustomerCode(currUser.getId());
            condition.setGoodsStandard(goodsStandardId);
            OrderShoppingCart orderShoppingCart = orderShoppingCartService.getByCondition(condition);
            if(null == orderShoppingCart) {
                orderShoppingCart = new OrderShoppingCart();
                orderShoppingCart.setGoodsId(goodsId);
                orderShoppingCart.setCustomerCode(currUser.getId());
                orderShoppingCart.setGoodsCount(1.00);
                orderShoppingCart.setMerchantCode(merchantCode);
                orderShoppingCart.setGoodsStandard(goodsStandard.getId());
                orderShoppingCart.setGoodsRecommendId(goodsRecommendCode);
            }else {
                orderShoppingCart.setGoodsCount(orderShoppingCart.getGoodsCount() + 1);
            }
            orderShoppingCartService.save(orderShoppingCart);
            renderString(response, ResultGenerator.genSuccessResult());
        }catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 删除购物车单件商品
     *
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "deleteShoppingCart", method = RequestMethod.POST)
    public void deleteShoppingCart(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String shoppingCartId = request.getParameter("shoppingCartId");
        try {
            if(null == currUser) {
                throw new ServiceException("未登录");
            }
            if(StringUtils.isBlank(shoppingCartId)) {
                throw new ServiceException("未选择购物车信息");
            }
            OrderShoppingCart condition = new OrderShoppingCart();
            condition.setId(shoppingCartId);
            orderShoppingCartService.delete(condition);
            renderString(response, ResultGenerator.genSuccessResult());
        }catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 调整购物车商品数量
     *
     * @param request 请求头
     * @param response 响应体
     */
    @RequestMapping(value = "adjustGoodsCount", method = RequestMethod.POST)
    public void adjustGoodsCount(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        String shoppingCartId = request.getParameter("shoppingCartId");
        String goodsCountStr = request.getParameter("goodsCount");
        try {
            if(null == currUser) {
                throw new ServiceException("未登录");
            }
            if(StringUtils.isBlank(shoppingCartId)) {
                throw new ServiceException("未选择购物车信息");
            }
            if(StringUtils.isBlank(goodsCountStr)) {
                throw new ServiceException("商品数量不能为空");
            }
            double goodsCount = Double.valueOf(goodsCountStr);
            OrderShoppingCart condition = new OrderShoppingCart();
            condition.setId(shoppingCartId);
            condition.setGoodsCount(goodsCount);
            if(goodsCount <= 0) {
                orderShoppingCartService.delete(condition);
            }else {
                orderShoppingCartService.save(condition);
            }
            renderString(response, ResultGenerator.genSuccessResult());
        }catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }

    /**
     * 购物车列表
     *
     * @param request 请求体
     * @param response 响应体
     */
    @RequestMapping(value = "shoppingCartList", method = RequestMethod.POST)
    public void shoppingCartList(HttpServletRequest request, HttpServletResponse response) {
        User currUser = UserUtils.getUser();
        try {
            if(null == currUser) {
                throw new ServiceException("未登录");
            }
            List<OrderShoppingCartVo> orderShoppingCartVos = orderShoppingCartService.findShoppingCartDetailList(currUser.getId());
            renderString(response, ResultGenerator.genSuccessResult(orderShoppingCartVos));
        }catch (Exception e) {
            renderString(response, ApiExceptionHandleUtil.normalExceptionHandle(e));
        }
    }
}
