package com.mall.modules.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.modules.member.entity.MemberInfo;
import com.mall.modules.member.service.MemberInfoService;
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

import java.util.ArrayList;
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

	@Autowired
	private MemberInfoService memberInfoService;
	
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
//		merchantInfoCheck.setCheckStatus("1");
		Page<MerchantInfoCheck> page = merchantInfoCheckService.findPage(new Page<MerchantInfoCheck>(request, response), merchantInfoCheck);
		model.addAttribute("page", page);
		return "modules/member/merchantInfoCheckList";
	}

	/**
	 * 商家列表
	 * @param merchantInfoCheck
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("member:merchantInfoCheck:view")
	@RequestMapping(value = {"memberList", ""})
	public String merchantList(MerchantInfoCheck merchantInfoCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		 merchantInfoCheck.setMerchantId(user.getId());
	    Page<MerchantInfoCheck> page = merchantInfoCheckService.findPage(new Page<MerchantInfoCheck>(request, response), merchantInfoCheck);
		model.addAttribute("page", page);
		return "modules/member/memberMerchantInfoCheckList";
	}

	/**
	 * 审核申请
	 * @param merchantInfoCheck
	 * @param model
	 * @return
	 */
	@RequiresPermissions("member:merchantInfoCheck:view")
	@RequestMapping(value = "form")
	public String form(MerchantInfoCheck merchantInfoCheck, Model model) {

        User user = UserUtils.getUser();
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setId(user.getId());
        memberInfo = memberInfoService.get(memberInfo);
        MerchantInfoCheck m = new MerchantInfoCheck();
        m.setCheckStatus("1");
        m.setMerchantId(user.getId());
        List<MerchantInfoCheck> list = merchantInfoCheckService.findList(m);
        if(null != list && list.size()>0){
            addMessage(model, "申请失败，已有在审核信息请耐心等待！");
            model.addAttribute("org","1");
            return "modules/member/merchantInfoCheckForm";
        }
		MerchantInfoCheck mm = new MerchantInfoCheck();
		mm.setAvatar(memberInfo.getAvatar());
		mm.setMerchantName(memberInfo.getNickname());
		mm.setMerchantHeadImg(memberInfo.getMerchantHeadImg());
		mm.setMerchantServicePhone(memberInfo.getMerchantServicePhone());
		model.addAttribute("merchantInfoCheck",mm);
		return "modules/member/merchantInfoCheckForm";
	}

	/**
	 * 审核详情
	 * @param merchantInfoCheck
	 * @param model
	 * @return
	 */
	@RequiresPermissions("member:merchantInfoCheck:view")
	@RequestMapping(value = "checkform")
	public String checkform(MerchantInfoCheck merchantInfoCheck, Model model) {
		merchantInfoCheck = merchantInfoCheckService.get(merchantInfoCheck.getId());
		model.addAttribute("merchantInfoCheck", merchantInfoCheck);
		return "modules/member/merchantInfoCheckDetails";
	}

	/**
	 * 审核
	 * @param merchantInfoCheck
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("member:merchantInfoCheck:view")
	@RequestMapping(value = "checkStatus")
	public String checkStatus(MerchantInfoCheck merchantInfoCheck, Model model, RedirectAttributes redirectAttributes) {

//		User user = UserUtils.getUser();
//		MerchantInfoCheck m = new MerchantInfoCheck();
//		m.setCheckStatus("1");
//		m.setMerchantId(user.getId());
//		List<MerchantInfoCheck> list = merchantInfoCheckService.findList(m);
//		if(null != list && list.size()>0){
//			addMessage(model, "申请失败，已有在审核信息请耐心等待！");
//			model.addAttribute("org","1");
//			return "modules/member/merchantInfoCheckForm";
//		}

		MerchantInfoCheck mic = merchantInfoCheckService.get(merchantInfoCheck.getId());
		mic.setCheckStatus(merchantInfoCheck.getCheckStatus());
		mic.setCheckRemark(merchantInfoCheck.getCheckRemark());
		merchantInfoCheckService.save(mic);
		if("2".equals(mic.getCheckStatus())){
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setId(mic.getMerchantId());
			memberInfo = memberInfoService.get(memberInfo);
			memberInfo.setNickname(mic.getMerchantName());
			memberInfo.setAvatar(mic.getAvatar());
			memberInfo.setHeadimgurl(mic.getAvatar());
			memberInfo.setMerchantHeadImg(mic.getMerchantHeadImg());
			memberInfo.setMerchantServicePhone(mic.getMerchantServicePhone());
			memberInfoService.save(memberInfo);
		}
		addMessage(redirectAttributes, "审核成功");
		return "redirect:"+Global.getAdminPath()+"/member/merchantInfoCheck/list?repage";
	}


	/**
	 * 提交审核
	 * @param merchantInfoCheck
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
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