package com.mall.modules.gift.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mall.common.utils.excel.ExportExcel;
import com.mall.modules.gift.entity.GiftMerchant;
import com.mall.modules.gift.service.GiftMerchantService;
import com.mall.modules.member.entity.MemberFeedback;
import com.mall.modules.sys.entity.User;
import com.mall.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.web.BaseController;
import com.mall.common.utils.StringUtils;
import com.mall.modules.gift.entity.GiftPurchaseLog;
import com.mall.modules.gift.service.GiftPurchaseLogService;

import java.util.Date;
import java.util.List;

/**
 * 礼包购买记录Controller
 * @author wankang
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftPurchaseLog")
public class GiftPurchaseLogController extends BaseController {

	@Autowired
	private GiftPurchaseLogService giftPurchaseLogService;
	@Autowired
	private GiftMerchantService giftMerchantService;
	
	@ModelAttribute
	public GiftPurchaseLog get(@RequestParam(required=false) String id) {
		GiftPurchaseLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftPurchaseLogService.get(id);
		}
		if (entity == null){
			entity = new GiftPurchaseLog();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftPurchaseLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftPurchaseLog giftPurchaseLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currUser = UserUtils.getUser();
		giftPurchaseLog.setMerchantCode(currUser.getId());
		giftPurchaseLog.setStatus("1");
		Page<GiftPurchaseLog> page = giftPurchaseLogService.findPage(new Page<GiftPurchaseLog>(request, response), giftPurchaseLog);
		model.addAttribute("page", page);
		return "modules/gift/giftPurchaseLogList";
	}

	@RequestMapping(value = {"exportData"})
	public void exportData(GiftPurchaseLog giftPurchaseLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currUser = UserUtils.getUser();
		giftPurchaseLog.setMerchantCode(currUser.getId());
		giftPurchaseLog.setStatus("1");
		List<GiftPurchaseLog> giftPurchaseLogs = giftPurchaseLogService.findList(giftPurchaseLog);
		ExportExcel exportExcel = new ExportExcel("礼包购买记录", GiftPurchaseLog.class);
		try {
			exportExcel.setDataList(giftPurchaseLogs);
			exportExcel.write(response, "礼包购买记录.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions("gift:giftPurchaseLog:view")
	@RequestMapping(value = {"operatorList"})
	public String operatorList(GiftPurchaseLog giftPurchaseLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GiftPurchaseLog> page = giftPurchaseLogService.findPage(new Page<GiftPurchaseLog>(request, response), giftPurchaseLog);
		model.addAttribute("page", page);
		return "modules/gift/giftPurchaseLogOperatorList";
	}

	@RequestMapping(value = {"operatorExportData"})
	public void operatorExportData(GiftPurchaseLog giftPurchaseLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<GiftPurchaseLog> giftPurchaseLogs = giftPurchaseLogService.findList(giftPurchaseLog);
		ExportExcel exportExcel = new ExportExcel("礼包购买记录", GiftPurchaseLog.class);
		try {
			exportExcel.setDataList(giftPurchaseLogs);
			exportExcel.write(response, "礼包购买记录.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions("gift:giftPurchaseLog:view")
	@RequestMapping(value = {"checkList"})
	public String checkList(GiftPurchaseLog giftPurchaseLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GiftPurchaseLog> page = giftPurchaseLogService.findPage(new Page<GiftPurchaseLog>(request, response), giftPurchaseLog);
		model.addAttribute("page", page);
		return "modules/gift/giftPurchaseCheckList";
	}

	@RequestMapping(value = "checkPass")
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String checkPass(GiftPurchaseLog giftPurchaseLog, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		String id = giftPurchaseLog.getId();
		if(StringUtils.isBlank(id)) {
			model.addAttribute("message", "未选择要审核的购买记录");
			return checkList(new GiftPurchaseLog(), request, response, model);
		}
		giftPurchaseLog = this.get(id);
		giftPurchaseLog.setStatus("1");
		giftPurchaseLog.setPayTime(new Date());
		GiftMerchant giftMerchant = giftPurchaseLogService.genGiftMerchant(giftPurchaseLog);
		giftMerchantService.save(giftMerchant);
		giftPurchaseLog.setGiftMerchantCode(giftMerchant.getId());
		giftPurchaseLogService.save(giftPurchaseLog);
		addMessage(redirectAttributes, "审核成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftPurchaseLog/checkList?repage";
	}

	@RequestMapping(value = "checkNotPass")
	public String checkNotPass(GiftPurchaseLog giftPurchaseLog, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		String id = giftPurchaseLog.getId();
		if(StringUtils.isBlank(id)) {
			model.addAttribute("message", "未选择要审核的购买记录");
			return checkList(new GiftPurchaseLog(), request, response, model);
		}
		giftPurchaseLog = this.get(id);
		giftPurchaseLog.setStatus("2");
		giftPurchaseLogService.save(giftPurchaseLog);
		addMessage(redirectAttributes, "审核成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftPurchaseLog/checkList?repage";
	}

	@RequiresPermissions("gift:giftPurchaseLog:view")
	@RequestMapping(value = "form")
	public String form(GiftPurchaseLog giftPurchaseLog, Model model) {
		model.addAttribute("giftPurchaseLog", giftPurchaseLog);
		return "modules/gift/giftPurchaseLogForm";
	}

	@RequiresPermissions("gift:giftPurchaseLog:edit")
	@RequestMapping(value = "save")
	public String save(GiftPurchaseLog giftPurchaseLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftPurchaseLog)){
			return form(giftPurchaseLog, model);
		}
		giftPurchaseLogService.save(giftPurchaseLog);
		addMessage(redirectAttributes, "保存礼包购买记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftPurchaseLog/?repage";
	}
	
	@RequiresPermissions("gift:giftPurchaseLog:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftPurchaseLog giftPurchaseLog, RedirectAttributes redirectAttributes) {
		giftPurchaseLogService.delete(giftPurchaseLog);
		addMessage(redirectAttributes, "删除礼包购买记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftPurchaseLog/?repage";
	}

}