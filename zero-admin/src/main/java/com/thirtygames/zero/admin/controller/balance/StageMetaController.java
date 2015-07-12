package com.thirtygames.zero.admin.controller.balance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.form.StageForm;
import com.thirtygames.zero.admin.validator.StageValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.model.meta.Stage;
import com.thirtygames.zero.common.service.admintool.balance.AdmStageMetaService;

@Controller
@RequestMapping(value = "/balance/stage")
public class StageMetaController extends AdminGridController<Stage, String, AdmStageMetaService, StageValidator> {
	public StageMetaController() {
		super("스테이지관리", "스테이지관리", null, AdminGridController.EMPTY_COLUMN_IDS, new StageForm());
		pageInfo.setTitle("Stage");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEtc);
		adminMenu.setSelectMenu("Stage");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("balance/stageMetaList");
		model.addAttribute("DataSources", DataSource.ZERO_META);
		model.addAttribute("baseUrl", "/balance/stage");
		return model;
	};
}
