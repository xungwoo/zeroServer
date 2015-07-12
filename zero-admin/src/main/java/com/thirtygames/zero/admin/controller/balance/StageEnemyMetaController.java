package com.thirtygames.zero.admin.controller.balance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.form.StageEnemyForm;
import com.thirtygames.zero.admin.validator.StageEnemyValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.model.meta.StageEnemy;
import com.thirtygames.zero.common.service.admintool.balance.AdmStageEnemyMetaService;

@Controller
@RequestMapping(value = "/balance/stageEnemy")
public class StageEnemyMetaController extends AdminGridController<StageEnemy, String, AdmStageEnemyMetaService, StageEnemyValidator> {
	public StageEnemyMetaController() {
		super("스테이지적군관리", "스테이지적군관리", null, AdminGridController.EMPTY_COLUMN_IDS, new StageEnemyForm());
		pageInfo.setTitle("Stage");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEtc);
		adminMenu.setSelectMenu("StageEnemy");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("balance/stageEnemyMetaList");
		model.addAttribute("DataSources", DataSource.ZERO_META);
		model.addAttribute("baseUrl", "/balance/stageEnemy");
		return model;
	};
}
