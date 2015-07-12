package com.thirtygames.zero.admin.controller.boss;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Joiner;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.BossRaidMetaValidator;
import com.thirtygames.zero.common.model.BossRaid;
import com.thirtygames.zero.common.model.admintool.AdminBossRaidMeta;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.admintool.boss.AdmBossRaidMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/boss/raid")
public class BossRaidMetaController extends AdminGridController<AdminBossRaidMeta, String, AdmBossRaidMetaService, BossRaidMetaValidator> {
	
	public BossRaidMetaController() {
		pageInfo.setTitle("BossRaidMeta");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Boss);
		adminMenu.setSelectMenu("BossRaidMeta");
		return adminMenu;
	}
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("boss/bossRaidMetaList");
		
		model.addAttribute("BossRaidStatusOpts", Joiner.on(";").withKeyValueSeparator(":").join(BossRaid.BossRaidStatus.get$CODE_LOOKUP()));
		model.addAttribute("RewardTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardType.get$CODE_LOOKUP()));
		model.addAttribute("baseUrl", "/boss/raid");
		return model;
	};	
	
	
	
	
}