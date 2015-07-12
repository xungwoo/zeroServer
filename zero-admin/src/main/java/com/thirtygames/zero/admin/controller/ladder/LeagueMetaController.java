package com.thirtygames.zero.admin.controller.ladder;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.LeagueMetaValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.model.admintool.AdminLeagueMeta;
import com.thirtygames.zero.common.service.admintool.ladder.AdmLeagueMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/league/meta")
public class LeagueMetaController extends AdminGridController<AdminLeagueMeta, String, AdmLeagueMetaService, LeagueMetaValidator> {
	
	public LeagueMetaController() {
		pageInfo.setTitle("LeagueMeta");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Ladder);
		adminMenu.setSelectMenu("LeagueMeta");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("ladder/leagueMetaList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		model.addAttribute("baseUrl", "/league/meta");
		return model;
	};
	
}
