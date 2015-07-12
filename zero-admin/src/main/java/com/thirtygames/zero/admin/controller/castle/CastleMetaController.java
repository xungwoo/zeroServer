package com.thirtygames.zero.admin.controller.castle;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Joiner;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.CastleMetaValidator;
import com.thirtygames.zero.common.model.admintool.AdminCastleMeta;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.admintool.castle.AdmCastleMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/castle")
public class CastleMetaController extends AdminGridController<AdminCastleMeta, String, AdmCastleMetaService, CastleMetaValidator> {
	
	public CastleMetaController() {
		pageInfo.setTitle("CastleMeta");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Castle);
		adminMenu.setSelectMenu("CastleMeta");
		return adminMenu;
	}
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("castle/castleMetaList");
		
		model.addAttribute("RewardTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardType.get$CODE_LOOKUP()));
		model.addAttribute("baseUrl", "/castle");
		return model;
	};	
	
	
	
	
}