package com.thirtygames.zero.admin.controller.boss;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.BossRaidMetaValidator;
import com.thirtygames.zero.common.etc.util.AjaxResponseUtil;
import com.thirtygames.zero.common.model.BossRaid;
import com.thirtygames.zero.common.model.admintool.AdminBossEventMeta;
import com.thirtygames.zero.common.model.admintool.AdminBossRaidMeta;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.admintool.boss.AdmBossEventMetaService;
import com.thirtygames.zero.common.service.admintool.boss.AdmBossRaidMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/boss/event")
public class BossEventMetaController extends AdminGridController<AdminBossEventMeta, String, AdmBossEventMetaService, BossRaidMetaValidator> {
	
	@Autowired
	AdmBossRaidMetaService bossRaidMetaService;
	
	public BossEventMetaController() {
		pageInfo.setTitle("BossEventMeta");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Boss);
		adminMenu.setSelectMenu("BossEventMeta");
		return adminMenu;
	}
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("boss/eventRaidMetaList");
		
		model.addAttribute("BossRaidStatusOpts", Joiner.on(";").withKeyValueSeparator(":").join(BossRaid.BossRaidStatus.get$CODE_LOOKUP()));
		model.addAttribute("RewardTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardType.get$CODE_LOOKUP()));
		model.addAttribute("baseUrl", "/boss/event");
		
		List<AdminBossRaidMeta> bossRaidMetaList = bossRaidMetaService.range(0, 0);
		log.debug("bossRaidMetaList::" + bossRaidMetaList);
		model.addAttribute("bossRaidMetaList", bossRaidMetaList);
		return model;
	};	
	
	
	
	@RequestMapping(value = "/add-by-preset", method = { RequestMethod.POST })
	public @ResponseBody ModelAndView addByPreset(@RequestParam("bossRaidMetaId") String bossRaidMetaId, ModelMap model)  {
		AdminBossRaidMeta presetMeta = bossRaidMetaService.get(bossRaidMetaId);
		service.addByPreset(presetMeta);
		
		return new ModelAndView(AjaxResponseUtil.jsonResult(Maps.newHashMap(), model));
	}	
	
	
	
	
}