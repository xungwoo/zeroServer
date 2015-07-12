package com.thirtygames.zero.admin.controller.account;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGenericController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.AccountValidator;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.service.AccountService;

@Slf4j
@Controller
@RequestMapping(value = "/shard")
public class ShardController extends AdminGenericController<Account, String, AccountService, AccountValidator> {
	
	public ShardController() {
		pageInfo.setTitle("EquipmentMeta");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEquip);
		adminMenu.setSelectMenu("EquipmentMeta");
		return adminMenu;
	}	
	
	
	
////	// @Autowired
////	// AccountStatService accountStatService;
//	
//	@Override
//	protected ModelMap postList(ModelMap model) {
//		super.setViewName("accountList");
//		return model;
//	}
	
//	@RequestMapping(value = "/new")
//	public ModelAndView accountNew() {
//
//		ConsoleAction action = new ConsoleAction();
//		action.setTitle("New Account");
//		action.addJsFile("js/data.js");
//
//		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("action", action);
////		model.put("topMenu", getDefaultAdminTopMenu());
////		model.put("leftMenu", getDefaultAdminLeftMenu4Account());
//		model.put("topSelection", "data");
//		model.put("selection", "account");
//		return new ModelAndView("accountNew", model);
//	}
//
//	@RequestMapping(value = "/properties/{accountId}")
//	public ModelAndView accountNew(@PathVariable("accountId") String accountId) {
//
//		ConsoleAction action = new ConsoleAction();
//		action.setTitle("Modify Account");
//		action.addJsFile("js/data.js");
//
//		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("action", action);
//		model.put("account", accountService.get(accountId));
////		model.put("topMenu", getDefaultAdminTopMenu());
////		model.put("leftMenu", getDefaultAdminLeftMenu4Account());
//		model.put("topSelection", "data");
//		model.put("selection", "account");
//		return new ModelAndView("accountProperties", model);
//	}
}