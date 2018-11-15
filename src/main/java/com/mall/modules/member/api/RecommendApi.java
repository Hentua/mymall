package com.mall.modules.member.api;

import com.alibaba.fastjson.JSONObject;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.Result;
import com.mall.common.utils.ResultGenerator;
import com.mall.common.utils.StringUtils;
import com.mall.common.web.BaseController;
import com.mall.modules.goods.entity.GoodsImage;
import com.mall.modules.goods.entity.GoodsInfo;
import com.mall.modules.goods.entity.GoodsStandard;
import com.mall.modules.goods.service.GoodsImageService;
import com.mall.modules.goods.service.GoodsInfoService;
import com.mall.modules.goods.service.GoodsStandardService;
import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping(value = "${adminPath}/")
public class RecommendApi extends BaseController {

	@Autowired
	private MemberInfoService memberInfoService;

	@RequestMapping(value = "registration")
	public String registration(HttpServletRequest request, Model model) {
		MemberInfo m  = new MemberInfo();
		m.setReferee(request.getParameter("recommendCode"));
		m = memberInfoService.get(m);
		model.addAttribute("refereeMember",m);
		model.addAttribute("API_IP",Global.getConfig("page.baseUrl"));
		return "modules/sys/registration";
	}

	/**
	 * 推荐二维码获取注册url地址接口
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/api/recommendUrl", method = RequestMethod.POST)
	public Result addGoodsEvaluate(HttpServletRequest request, HttpServletResponse response) {
		User user = UserUtils.getUser();
		JSONObject jo = new JSONObject();
		jo.put("url",Global.getConfig("page.baseUrl")+"registration?recommendCode="+user.getReferee());
		return ResultGenerator.genSuccessResult(jo);
	}
}