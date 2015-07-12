package com.thirtygames.zero.admin.controller.event;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Joiner;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.WellcomePresentValidator;
import com.thirtygames.zero.common.model.admintool.AdminWellcomePresent;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.admintool.event.AdmWellcomePresentService;

@Slf4j
@Controller
@RequestMapping(value = "/event/wellcome-present")
public class WellcomePresentMetaController extends AdminGridController<AdminWellcomePresent, String, AdmWellcomePresentService, WellcomePresentValidator> {
	
	public WellcomePresentMetaController() {
		pageInfo.setTitle("WellcomePresent");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Event);
		adminMenu.setSelectMenu("WellcomePresent");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("event/wellcomePresentList");
		model.addAttribute("baseUrl", "/event/wellcome-present");
		model.addAttribute("RewardTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardType.get$CODE_LOOKUP()));
		return model;
	};
	
}
