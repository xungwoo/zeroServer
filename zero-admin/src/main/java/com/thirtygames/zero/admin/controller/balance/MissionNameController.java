package com.thirtygames.zero.admin.controller.balance;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Joiner;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.MissionMetaValidator;
import com.thirtygames.zero.common.model.admintool.AdminMissionMeta;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.admintool.balance.AdmMissionNameMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/balance/mission-name")
public class MissionNameController extends AdminGridController<AdminMissionMeta, String, AdmMissionNameMetaService, MissionMetaValidator> {
	
	public MissionNameController() {
		pageInfo.setTitle("MissionName");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEtc);
		adminMenu.setSelectMenu("MissionName");
		return adminMenu;
	}
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("balance/missionNameMetaList");
		
		model.addAttribute("RewardTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardType.get$CODE_LOOKUP()));
		//model.addAttribute("baseUrl", "/balance/equipment");
		return model;
	};	
	
	
	
	
}