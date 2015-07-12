package com.thirtygames.zero.admin.controller.version;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.ApiMetaValidator;
import com.thirtygames.zero.common.model.admintool.AdminApiMeta;
import com.thirtygames.zero.common.service.admintool.version.AdmApiMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/version/api-meta")
public class ApiMetaController extends AdminGridController<AdminApiMeta, String, AdmApiMetaService, ApiMetaValidator> {
	
	public ApiMetaController() {
		pageInfo.setTitle("ApiMeta");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Version);
		adminMenu.setSelectMenu("ApiMeta");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("version/apiMetaList");
		return model;
	};
	
}
