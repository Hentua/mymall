package com.mall.modules.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.mall.modules.member.entity.MerchantInfoCheck;
import com.mall.modules.member.service.MerchantInfoCheckService;

import java.util.List;

/**
 * 店铺信息审核Controller
 * @author hub
 * @version 2018-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/member/merchantInfoCheck")
public class MerchantInfoCheckController extends BaseController {

	@Autowired
	private MerchantInfoCheckService merchantInfoCheckService;
	
	@ModelAttribute
	public MerchantInfoCheck get(@RequestParam(required=false) String id) {
		MerchantInfoCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantInfoCheckService.get(id);
		}
		if (entity == null){
			entity = new MerchantInfoCheck();
		}
		return entity;
	}
	
	@RequiresPermissions("member:merchantInfoCheck:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantInfoCheck merchantInfoCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MerchantInfoCheck> page = merchantInfoCheckService.findPage(new Page<MerchantInfoCheck>(request, response), merchantInfoCheck); 
		model.addAttribute("page", page);
		return "modules/member/merchantInfoCheckList";
	}

	@RequiresPermissions("member:merchantInfoCheck:view")
	@RequestMapping(value = {"memberList", ""})
	public String merchantList(MerchantInfoCheck merchantInfoCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		 merchantInfoCheck.setMerchantId(user.getId());
	    Page<MerchantInfoCheck> page = merchantInfoCheckService.findPage(new Page<MerchantInfoCheck>(request, response), merchantInfoCheck);
		model.addAttribute("page", page);
		return "modules/member/memberMerchantInfoCheckList";
	}

	@RequiresPermissions("member:merchantInfoCheck:view")
	@RequestMapping(value = "form")
	public String form(MerchantInfoCheck merchantInfoCheck, Model model) {

        User user = UserUtils.getUser();
        MerchantInfoCheck m = new MerchantInfoCheck();
        m.setCheckStatus("1");
        m.setMerchantId(user.getId());
        List<MerchantInfoCheck> list = merchantInfoCheckService.findList(m);
        if(null != list && list.size()>0){
            addMessage(model, "申请失败，已有在审核信息请耐心等待！");
            model.addAttribute("org","1");
            return "modules/member/merchantInfoCheckForm";
        }
		model.addAttribute("merchantInfoCheck", merchantInfoCheck);
		return "modules/member/merchantInfoCheckForm";
	}

	@RequiresPermissions("member:merchantInfoCheck:edit")
	@RequestMapping(value = "save")
	public String save(MerchantInfoCheck merchantInfoCheck, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantInfoCheck)){
			return form(merchantInfoCheck, model);
		}
        User user = UserUtils.getUser();
		merchantInfoCheck.setCheckStatus("1");
        merchantInfoCheck.setMerchantId(user.getId());
		merchantInfoCheckService.save(merchantInfoCheck);
		addMessage(redirectAttributes, "提交店铺信息变更申请成功");
		return "redirect:"+Global.getAdminPath()+"/member/merchantInfoCheck/memberList?repage";
	}
	
	@RequiresPermissions("member:merchantInfoCheck:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantInfoCheck merchantInfoCheck, RedirectAttributes redirectAttributes) {
		merchantInfoCheckService.delete(merchantInfoCheck);
		addMessage(redirectAttributes, "删除店铺信息审核成功");
		return "redirect:"+Global.getAdminPath()+"/member/merchantInfoCheck/?repage";
	}

}