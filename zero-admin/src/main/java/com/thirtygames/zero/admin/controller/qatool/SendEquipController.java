package com.thirtygames.zero.admin.controller.qatool;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminBaseController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.common.service.AccountService;
import com.thirtygames.zero.common.service.StageService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;

@Slf4j
@Controller
@RequestMapping(value = "/qatool/send-equip")
public class SendEquipController extends AdminBaseController {
	
	@Autowired
	DataSourceService dsManager;
	
	@Autowired
	StageService scService;
	
	@Autowired
	AccountService accountService;

	public SendEquipController() {
		pageInfo.setTitle("SendEquipTool");
	}

	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.QATool);
		adminMenu.setSelectMenu("SendEquipment");
		return adminMenu;
	}
//
//	@RequestMapping(method = { RequestMethod.GET })
//	public ModelAndView stageClearTool(ModelMap model) {
//		return new ModelAndView("qatool/stageClearTool");
//	}
//
//	@RequestMapping(method = { RequestMethod.POST })
//	public @ResponseBody ModelAndView stageClear(@ModelAttribute StageClearTool stageClearTool, BindingResult result, SessionStatus status, HttpServletRequest request, ModelMap model) {
//		log.debug("stageClearTool:;" + stageClearTool);
//		
//		dsManager.switchDataSource(stageClearTool.getAccountId());
//		Account account = accountService.getAccountDynamic(stageClearTool.getAccountId());
//		ValidationUtil.isNullModel(account);
//		
//		scService.saveStageClearDynamic(stageClearTool);
//		return new ModelAndView(AjaxResponseUtil.jsonResult(Maps.newHashMap(), model));
//	}
}
