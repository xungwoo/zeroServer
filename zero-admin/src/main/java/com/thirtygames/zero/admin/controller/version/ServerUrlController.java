package com.thirtygames.zero.admin.controller.version;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.ServerUrlValidator;
import com.thirtygames.zero.common.model.meta.ServerInfo;
import com.thirtygames.zero.common.service.admintool.version.AdmServerUrlService;

@Slf4j
@Controller
@RequestMapping(value = "/version/server-url")
public class ServerUrlController extends AdminGridController<ServerInfo, String, AdmServerUrlService, ServerUrlValidator> {
	
	public ServerUrlController() {
		pageInfo.setTitle("ServerUrl");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Version);
		adminMenu.setSelectMenu("ServerUrl");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("version/serverUrlList");
		return model;
	};
	
}
