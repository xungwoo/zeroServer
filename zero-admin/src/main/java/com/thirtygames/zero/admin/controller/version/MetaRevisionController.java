package com.thirtygames.zero.admin.controller.version;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.MetaRevisionValidator;
import com.thirtygames.zero.common.model.meta.MetaRevision;
import com.thirtygames.zero.common.service.admintool.version.AdmMetaRevisionService;

@Slf4j
@Controller
@RequestMapping(value = "/version/meta-revision")
public class MetaRevisionController extends AdminGridController<MetaRevision, String, AdmMetaRevisionService, MetaRevisionValidator> {
	
	public MetaRevisionController() {
		pageInfo.setTitle("MetaRevision");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Version);
		adminMenu.setSelectMenu("MetaRevision");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("version/metaRevisionList");
		return model;
	};
	
}
