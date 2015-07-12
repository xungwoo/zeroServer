package com.thirtygames.zero.admin.controller.event;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.CouponValidator;
import com.thirtygames.zero.common.model.admintool.CouponTemplate;
import com.thirtygames.zero.common.service.admintool.event.AdmCouponTemplateService;

@Slf4j
@Controller
@RequestMapping(value = "/event/coupon/template")
public class CouponTemplateController extends AdminGridController<CouponTemplate, String, AdmCouponTemplateService, CouponValidator> {
	
	public CouponTemplateController() {
		pageInfo.setTitle("CouponTemplate");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Event);
		adminMenu.setSelectMenu("CouponTemplate");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("event/couponTemplateList");
		return model;
	};
	
}
