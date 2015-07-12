package com.thirtygames.zero.admin.controller.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Joiner;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.ResourceLogValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.model.log.ResourceLog;
import com.thirtygames.zero.common.service.admintool.log.AdmResourceLogService;

@Slf4j
@Controller
@RequestMapping(value = "/log/resource")
public class ResourceLogController extends AdminGridController<ResourceLog, String, AdmResourceLogService, ResourceLogValidator> {
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Log);
		adminMenu.setSelectMenu("ResourceLog");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("log/resourceLogList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		
		model.addAttribute("StatusOpts", Joiner.on(";").withKeyValueSeparator(":").join(ResourceLog.Status.get$CODE_LOOKUP()));
		return model;
	};	
}
