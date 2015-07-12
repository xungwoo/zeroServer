package com.thirtygames.zero.admin.controller.event;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.DropRateValidator;
import com.thirtygames.zero.common.model.admintool.AdminEventDropRate;
import com.thirtygames.zero.common.service.admintool.event.AdmDropRateService;

@Slf4j
@Controller
@RequestMapping(value = "/event/drop-rate")
public class DropRateController extends AdminGridController<AdminEventDropRate, String, AdmDropRateService, DropRateValidator> {
	
	public DropRateController() {
		pageInfo.setTitle("EventDropRate");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Event);
		adminMenu.setSelectMenu("DropRate");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("event/dropRateList");
		return model;
	};
	
}
