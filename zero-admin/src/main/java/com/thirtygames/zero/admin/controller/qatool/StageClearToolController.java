package com.thirtygames.zero.admin.controller.qatool;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminBaseController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.common.etc.util.AjaxResponseUtil;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.qatool.StageClearTool;
import com.thirtygames.zero.common.service.AccountService;
import com.thirtygames.zero.common.service.StageService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;

@Slf4j
@Controller
@RequestMapping(value = "/qatool/stage-clear-tool")
public class StageClearToolController extends AdminBaseController {
	
	@Autowired
	DataSourceService dsManager;
	
	@Autowired
	StageService scService;
	
	@Autowired
	AccountService accountService;

	public StageClearToolController() {
		pageInfo.setTitle("StageClearTool");
	}

	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.QATool);
		adminMenu.setSelectMenu("StageClear");
		return adminMenu;
	}

	@RequestMapping(method = { RequestMethod.GET })
	public ModelAndView stageClearTool(ModelMap model) {
		return new ModelAndView("qatool/stageClearTool");
	}

	@RequestMapping(method = { RequestMethod.POST })
	public @ResponseBody ModelAndView stageClear(@ModelAttribute StageClearTool stageClearTool, BindingResult result, SessionStatus status, HttpServletRequest request, ModelMap model) {
		log.debug("stageClearTool:;" + stageClearTool);
		
		dsManager.switchDataSource(stageClearTool.getAccountId());
		Account account = accountService.getAccountDynamic(stageClearTool.getAccountId());
		ValidationUtil.isNullModel(account);
		
		scService.saveStageClearDynamic(stageClearTool);
		return new ModelAndView(AjaxResponseUtil.jsonResult(Maps.newHashMap(), model));
	}
}
