package com.mall.modules.sys.api;

import com.mall.common.utils.ResultGenerator;
import com.mall.common.web.BaseController;
import com.mall.modules.sys.entity.SysUpgrade;
import com.mall.modules.sys.service.SysUpgradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/api/upgrade")
public class UpgradeApi extends BaseController {

    @Autowired
    private SysUpgradeService sysUpgradeService;

    /**
     * 升级信息
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public void info(HttpServletRequest request, HttpServletResponse response) {
        SysUpgrade sysUpgrade = new SysUpgrade();
        sysUpgrade.setOs(request.getParameter("os"));
        List<SysUpgrade> list = sysUpgradeService.findList(sysUpgrade);
        if(null == list ||  list.size() ==0 ){
            renderString(response, ResultGenerator.genFailResult("暂无升级信息"));
        }
        renderString(response, ResultGenerator.genSuccessResult(list.get(0)));
    }


}
