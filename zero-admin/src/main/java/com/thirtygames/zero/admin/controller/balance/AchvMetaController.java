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
import com.thirtygames.zero.admin.validator.AchvMetaValidator;
import com.thirtygames.zero.common.model.admintool.AdminAchvMeta;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.admintool.balance.AdmAchvMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/balance/achievement")
public class AchvMetaController extends AdminGridController<AdminAchvMeta, String, AdmAchvMetaService, AchvMetaValidator> {
	
	public AchvMetaController() {
		pageInfo.setTitle("AchievementMeta");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEtc);
		adminMenu.setSelectMenu("AchievementMeta");
		return adminMenu;
	}
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("balance/achvMetaList");
	
		model.addAttribute("RewardTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardType.get$CODE_LOOKUP()));
		return model;
	};	
	
	
	
	
}