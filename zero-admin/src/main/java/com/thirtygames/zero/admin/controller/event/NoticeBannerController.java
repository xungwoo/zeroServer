package com.thirtygames.zero.admin.controller.event;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.NoticeBannerValidator;
import com.thirtygames.zero.common.model.admindata.Notice;
import com.thirtygames.zero.common.service.admintool.event.AdmNoticeBannerService;

@Slf4j
@Controller
@RequestMapping(value = "/event/notice-banner")
public class NoticeBannerController extends AdminGridController<Notice, String, AdmNoticeBannerService, NoticeBannerValidator> {
	
	public NoticeBannerController() {
		pageInfo.setTitle("NoticeBanner");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Event);
		adminMenu.setSelectMenu("NoticeBanner");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("event/noticeBannerList");
		return model;
	};
	
}
