package com.thirtygames.zero.admin.controller.version;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.ClientInfoValidator;
import com.thirtygames.zero.common.model.meta.ClientInfo;
import com.thirtygames.zero.common.service.admintool.version.AdmClientInfoService;

@Slf4j
@Controller
@RequestMapping(value = "/version/client-info")
public class ClientInfoController extends AdminGridController<ClientInfo, String, AdmClientInfoService, ClientInfoValidator> {
	
	public ClientInfoController() {
		pageInfo.setTitle("ClientInfo");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Version);
		adminMenu.setSelectMenu("ClientInfo");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("version/clientVersionList");
		return model;
	};
	
}
