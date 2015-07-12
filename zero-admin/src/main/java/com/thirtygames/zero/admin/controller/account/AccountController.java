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
@RequestMapping(value = "/account")
public class AccountController extends AdminGenericController<Account, String, AccountService, AccountValidator> {
	
	public AccountController() {
		pageInfo.setTitle("EquipmentMeta");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEquip);
		adminMenu.setSelectMenu("EquipmentMeta");
		return adminMenu;
	}	
}