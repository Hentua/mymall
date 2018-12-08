package com.mall.modules.gift.web;

import com.google.common.collect.Lists;
import com.mall.common.config.Global;
import com.mall.common.persistence.Page;
import com.mall.common.utils.StringUtils;
import com.mall.common.utils.excel.ExportExcel;
import com.mall.common.web.BaseController;
import com.mall.modules.gift.entity.GiftConfigCategory;
import com.mall.modules.gift.entity.GiftTransferLog;
import com.mall.modules.gift.service.GiftConfigCategoryService;
import com.mall.modules.gift.service.GiftTransferLogService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.mall.common.service.BaseService.dataScopeFilter;

/**
 * 礼包赠送记录Controller
 * @author wankang
 * @version 2018-11-07
 */
@Controller
@RequestMapping(value = "${adminPath}/gift/giftTransferLog")
public class GiftTransferLogController extends BaseController {

	@Autowired
	private GiftTransferLogService giftTransferLogService;
	@Autowired
	private GiftConfigCategoryService giftConfigCategoryService;
	
	@ModelAttribute
	public GiftTransferLog get(@RequestParam(required=false) String id) {
		GiftTransferLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = giftTransferLogService.get(id);
		}
		if (entity == null){
			entity = new GiftTransferLog();
		}
		return entity;
	}
	
	@RequiresPermissions("gift:giftTransferLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(GiftTransferLog giftTransferLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		User currUser = UserUtils.getUser();
		giftTransferLog.setMerchantCode(currUser.getId());
		Page<GiftTransferLog> page = giftTransferLogService.findPage(new Page<GiftTransferLog>(request, response), giftTransferLog);
		model.addAttribute("page", page);
		return "modules/gift/giftTransferLogList";
	}

	@RequestMapping(value = {"exportData"})
	public void exportData(GiftTransferLog giftTransferLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		String [] itemIds = request.getParameterValues("itemIds");
		User currUser = UserUtils.getUser();
		giftTransferLog.setMerchantCode(currUser.getId());
		List<GiftTransferLog> giftTransferLogs;
		if(null != itemIds && itemIds.length > 0) {
			giftTransferLogs = Lists.newArrayList();
			for (String itemId : itemIds) {
				giftTransferLogs.add(this.get(itemId));
			}
		}else {
			giftTransferLogs = giftTransferLogService.findList(giftTransferLog);
		}

		ExportExcel exportExcel = new ExportExcel("礼包赠送记录", GiftTransferLog.class);
		try {
			exportExcel.setDataList(giftTransferLogs);
			exportExcel.write(response, "礼包赠送记录.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions("gift:giftTransferLog:view")
	@RequestMapping(value = {"operatorList"})
	public String operatorList(GiftTransferLog giftTransferLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		giftTransferLog.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "uoo", "uo"));
		Page<GiftTransferLog> page = giftTransferLogService.findPage(new Page<GiftTransferLog>(request, response), giftTransferLog);
		model.addAttribute("page", page);
		model.addAttribute("giftConfigCategoryList", giftConfigCategoryService.findList(new GiftConfigCategory()));
		return "modules/gift/giftTransferLogOperatorList";
	}

	@RequestMapping(value = {"operatorExportData"})
	public void operatorExportData(GiftTransferLog giftTransferLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		giftTransferLog.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "uoo", "uo"));
		String [] itemIds = request.getParameterValues("itemIds");
		List<GiftTransferLog> giftTransferLogs;
		if(null != itemIds && itemIds.length > 0) {
			giftTransferLogs = Lists.newArrayList();
			for (String itemId : itemIds) {
				giftTransferLogs.add(this.get(itemId));
			}
		}else {
			giftTransferLogs = giftTransferLogService.findList(giftTransferLog);
		}

		ExportExcel exportExcel = new ExportExcel("礼包赠送记录", GiftTransferLog.class);
		try {
			exportExcel.setDataList(giftTransferLogs);
			exportExcel.write(response, "礼包赠送记录.xlsx");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions("gift:giftTransferLog:view")
	@RequestMapping(value = "form")
	public String form(GiftTransferLog giftTransferLog, Model model) {
		model.addAttribute("giftTransferLog", giftTransferLog);
		return "modules/gift/giftTransferLogForm";
	}

	@RequiresPermissions("gift:giftTransferLog:edit")
	@RequestMapping(value = "save")
	public String save(GiftTransferLog giftTransferLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, giftTransferLog)){
			return form(giftTransferLog, model);
		}
		giftTransferLogService.save(giftTransferLog);
		addMessage(redirectAttributes, "保存礼包赠送记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftTransferLog/?repage";
	}
	
	@RequiresPermissions("gift:giftTransferLog:edit")
	@RequestMapping(value = "delete")
	public String delete(GiftTransferLog giftTransferLog, RedirectAttributes redirectAttributes) {
		giftTransferLogService.delete(giftTransferLog);
		addMessage(redirectAttributes, "删除礼包赠送记录成功");
		return "redirect:"+Global.getAdminPath()+"/gift/giftTransferLog/?repage";
	}

}