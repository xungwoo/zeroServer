package com.thirtygames.zero.admin.controller.balance.equip;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGenericController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.EquipChoiceDecoMetaValidator;
import com.thirtygames.zero.common.model.equipment.meta.EquipChoiceDecoMeta;
import com.thirtygames.zero.common.service.equipment.meta.EquipChoiceDecoMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/balance/equipment/decostat-rate")
public class EquipChoiceDecoMetaController extends AdminGenericController<EquipChoiceDecoMeta, String, EquipChoiceDecoMetaService, EquipChoiceDecoMetaValidator> {
	
	public EquipChoiceDecoMetaController() {
		pageInfo.setTitle("EquipChoiceDecoStat");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEquip);
		adminMenu.setSelectMenu("EquipChoiceDecoStat");
		return adminMenu;
	}
	
	
	protected ModelMap postGet(ModelMap model) {
		super.setViewName("balance/equipDecoStatRate");
		model.addAttribute("baseUrl", "/balance/equipment/decostat-rate");
		return model;
	};	
	
	protected ModelMap postAdd(ModelMap model) {
		super.setRedirectUrl("/zero-admin/balance/equipment/decostat-rate/1");
		return model;
	};	
	
}