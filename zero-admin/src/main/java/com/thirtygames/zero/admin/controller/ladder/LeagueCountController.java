package com.thirtygames.zero.admin.controller.ladder;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.LeagueCountValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.model.meta.LeagueCount;
import com.thirtygames.zero.common.service.admintool.user.AdmLeagueCountService;

@Slf4j
@Controller
@RequestMapping(value = "/league/count")
public class LeagueCountController extends AdminGridController<LeagueCount, Integer, AdmLeagueCountService, LeagueCountValidator> {
	
	public LeagueCountController() {
		pageInfo.setTitle("LeagueCount");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Ladder);
		adminMenu.setSelectMenu("LeagueCount");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("ladder/leagueCountList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		return model;
	};
	
}
