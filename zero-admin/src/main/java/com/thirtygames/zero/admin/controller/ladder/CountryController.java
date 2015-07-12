package com.thirtygames.zero.admin.controller.ladder;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.CountryValidator;
import com.thirtygames.zero.common.model.admintool.AdminCountry;
import com.thirtygames.zero.common.service.admintool.AdmCountryService;

@Slf4j
@Controller
@RequestMapping(value = "/league/country")
public class CountryController extends AdminGridController<AdminCountry, String, AdmCountryService, CountryValidator> {
	
	public CountryController() {
		pageInfo.setTitle("LadderCountry");
		pageInfo.setPageSize(10);
	}
	
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Ladder);
		adminMenu.setSelectMenu("Country");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("ladder/countryList");
		return model;
	};
	
}
