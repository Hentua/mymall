package com.mall.modules.notice.web;

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
import com.mall.modules.notice.entity.IndexNotice;
import com.mall.modules.notice.service.IndexNoticeService;

/**
 * 首页公告Controller
 * @author hub
 * @version 2018-11-25
 */
@Controller
@RequestMapping(value = "${adminPath}/notice/indexNotice")
public class IndexNoticeController extends BaseController {

	@Autowired
	private IndexNoticeService indexNoticeService;
	
	@ModelAttribute
	public IndexNotice get(@RequestParam(required=false) String id) {
		IndexNotice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = indexNoticeService.get(id);
		}
		if (entity == null){
			entity = new IndexNotice();
		}
		return entity;
	}
	
	@RequiresPermissions("notice:indexNotice:view")
	@RequestMapping(value = {"list", ""})
	public String list(IndexNotice indexNotice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<IndexNotice> page = indexNoticeService.findPage(new Page<IndexNotice>(request, response), indexNotice); 
		model.addAttribute("page", page);
		return "modules/notice/indexNoticeList";
	}

	@RequiresPermissions("notice:indexNotice:view")
	@RequestMapping(value = "form")
	public String form(IndexNotice indexNotice, Model model) {
		model.addAttribute("indexNotice", indexNotice);
		return "modules/notice/indexNoticeForm";
	}


	@RequestMapping(value = "info")
	public String info(IndexNotice indexNotice, Model model) {
		indexNotice = indexNoticeService.get(indexNotice.getId());
		model.addAttribute("indexNotice", indexNotice);
		return "modules/notice/indexNoticeInfo";
	}

	@RequiresPermissions("notice:indexNotice:edit")
	@RequestMapping(value = "save")
	public String save(IndexNotice indexNotice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, indexNotice)){
			return form(indexNotice, model);
		}
		User user = UserUtils.getUser();
		indexNotice.setCreateUser(user.getId());
		indexNoticeService.save(indexNotice);
		addMessage(redirectAttributes, "保存首页公告成功");
		return "redirect:"+Global.getAdminPath()+"/notice/indexNotice/?repage";
	}
	
	@RequiresPermissions("notice:indexNotice:edit")
	@RequestMapping(value = "delete")
	public String delete(IndexNotice indexNotice, RedirectAttributes redirectAttributes) {
		indexNoticeService.delete(indexNotice);
		addMessage(redirectAttributes, "删除首页公告成功");
		return "redirect:"+Global.getAdminPath()+"/notice/indexNotice/?repage";
	}

}