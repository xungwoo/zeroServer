package com.thirtygames.zero.admin.controller.balance;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.MissionMetaValidator;
import com.thirtygames.zero.common.model.admintool.AdminMissionMeta;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.admintool.balance.AdmMissionMetaService;
import com.thirtygames.zero.common.service.admintool.balance.AdmMissionNameMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/balance/mission")
public class MissionMetaController extends AdminGridController<AdminMissionMeta, String, AdmMissionMetaService, MissionMetaValidator> {
	
	@Autowired
	AdmMissionNameMetaService nameMetaService;
	
	public MissionMetaController() {
		pageInfo.setTitle("MissionMeta");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEtc);
		adminMenu.setSelectMenu("MissionMeta");
		return adminMenu;
	}
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("balance/missionMetaList");
	
		List<AdminMissionMeta> nameList = nameMetaService.range(0, 0);
		log.debug("nameList::" + nameList);
		String nameOpts = "";
		for(AdminMissionMeta nameInfo : nameList) {
			nameOpts += nameInfo.getNameKey() + ":" + nameInfo.getNameKey() + "/" + nameInfo.getTitleKo() + ";";
		}	
		nameOpts = CharMatcher.anyOf(";").trimFrom(nameOpts);		
		log.debug("nameOpts::" + nameOpts);
		model.addAttribute("nameOpts", nameOpts);
		model.addAttribute("RewardTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardType.get$CODE_LOOKUP()));
		return model;
	};	
	
	
	
	
}