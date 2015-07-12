package com.thirtygames.zero.admin.controller.balance.equip;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.EquipLevelMetaValidator;
import com.thirtygames.zero.common.model.admintool.AdminEquipLevelMeta;
import com.thirtygames.zero.common.service.admintool.balance.AdmEquipLevelMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/balance/equipment/level")
public class EquipLevelController extends AdminGridController<AdminEquipLevelMeta, String, AdmEquipLevelMetaService, EquipLevelMetaValidator> {
	
	public EquipLevelController() {
		pageInfo.setTitle("EquipLevel");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEquip);
		adminMenu.setSelectMenu("EquipLevel");
		return adminMenu;
	}
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("balance/equipLevelMeta");
		return model;
	};	
	
}