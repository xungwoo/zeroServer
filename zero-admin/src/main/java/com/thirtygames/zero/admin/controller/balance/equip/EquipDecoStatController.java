package com.thirtygames.zero.admin.controller.balance.equip;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.EquipDecoStatValidator;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatMeta;
import com.thirtygames.zero.common.service.equipment.meta.EquipDecoStatService;

@Slf4j
@Controller
@RequestMapping(value = "/balance/equipment/decostat")
public class EquipDecoStatController extends AdminGridController<EquipStatMeta, String, EquipDecoStatService, EquipDecoStatValidator> {
	
	public EquipDecoStatController() {
		pageInfo.setTitle("EquipDecoStat");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEquip);
		adminMenu.setSelectMenu("EquipDecoStat");
		return adminMenu;
	}
	
	
	protected ModelMap postGrid(ModelMap model) {
		//model.addAttribute("equipDecoTypeList", EquipmentDecorationType.getTypeStr());
		//model.addAttribute("equipTypeList", EquipType.getTypeStr(service.getEquipTypeList()));
		//model.addAttribute("equipCategoryTypeList", EquipType.getCategoryStr(emService.getEquipCategoryTypeList()));
		
		super.setViewName("balance/equipDecoStatList");
		model.addAttribute("baseUrl", "/balance/equipment/decostat");
		return model;
	};		
	
}