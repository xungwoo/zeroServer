package com.thirtygames.zero.admin.controller.stats;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.StageStatsValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.model.admintool.AdminStageStats;
import com.thirtygames.zero.common.service.admintool.stats.AdmStageStatsService;

@Slf4j
@Controller
@RequestMapping(value = "/stats/stage")
public class StageStatsController extends AdminGridController<AdminStageStats, String, AdmStageStatsService, StageStatsValidator> {
	
	public StageStatsController() {
		pageInfo.setTitle("StageStats");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Stats);
		adminMenu.setSelectMenu("StageStats");
		return adminMenu;
	}
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("stats/stageStatsList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		
		return model;
	};	
	
	
//	@ModelAttribute("equipTypeListJson")
//	public String getEquipTypeList() {
//		return JacksonUtil.toJson(commonCodeService.getEquipNameList());
//	}	
	
	
	
}