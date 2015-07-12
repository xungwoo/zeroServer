package com.thirtygames.zero.admin.controller.balance.equip;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.EquipTypeValidator;
import com.thirtygames.zero.common.model.equipment.meta.EquipType;
import com.thirtygames.zero.common.service.equipment.meta.EquipMetaService;
import com.thirtygames.zero.common.service.equipment.meta.EquipTypeService;

@Slf4j
@Controller
@RequestMapping(value = "/balance/equipment/type")
public class EquipTypeController extends AdminGridController<EquipType, String, EquipTypeService, EquipTypeValidator> {
	@Autowired
	EquipMetaService emService;
	
	public EquipTypeController() {
		pageInfo.setTitle("EquipmentType");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEquip);
		adminMenu.setSelectMenu("EquipmentType");
		return adminMenu;
	}
	
	
	protected ModelMap postGrid(ModelMap model) {
		model.addAttribute("equipSubCategoryTypeList", EquipType.getSubCategoryStr(emService.getEquipSubCategoryTypeList(0)));
		model.addAttribute("equipCategoryTypeList", EquipType.getCategoryStr(emService.getEquipCategoryTypeList()));
		
		super.setViewName("balance/equipmentList");
		model.addAttribute("baseUrl", "/balance/equipment/type");
		return model;
	};	
	
}