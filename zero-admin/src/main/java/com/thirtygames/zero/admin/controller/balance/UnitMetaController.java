package com.thirtygames.zero.admin.controller.balance;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.UnitMetaValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.model.admintool.AdminUnitMeta;
import com.thirtygames.zero.common.model.meta.UnitMeta;
import com.thirtygames.zero.common.service.admintool.balance.AdmUnitMetaService;

@Controller
@RequestMapping(value = "/balance/unit")
public class UnitMetaController
		extends
		AdminGridController<AdminUnitMeta, String, AdmUnitMetaService, UnitMetaValidator> {
	public UnitMetaController() {
		super("유닛메타관리", "유닛메타관리", null, AdminGridController.EMPTY_COLUMN_IDS, null);
				//new UnitMeataForm());
		pageInfo.setTitle("Unit");
		pageInfo.setPageSize(10);
	}

	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEtc);
		adminMenu.setSelectMenu("UnitMeta");
		return adminMenu;
	}

	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("balance/unitMetaList");
		model.addAttribute("DataSources", DataSource.ZERO_META);
		model.addAttribute("baseUrl", "/balance/unit");
		return model;
	};
}
