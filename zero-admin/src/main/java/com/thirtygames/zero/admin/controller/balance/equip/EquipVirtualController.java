package com.thirtygames.zero.admin.controller.balance.equip;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.thirtygames.zero.admin.controller.common.AdminBaseController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatType;
import com.thirtygames.zero.common.model.equipment.meta.EquipType;
import com.thirtygames.zero.common.service.equipment.meta.EquipMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/balance/equipment/virtual")
public class EquipVirtualController extends AdminBaseController {

	@Autowired
	EquipMetaService emService;

	
	public EquipVirtualController() {
		pageInfo.setTitle("EquipmentVirtual");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEquip);
		adminMenu.setSelectMenu("EquipmentVirtual");
		return adminMenu;
	}	
	
	
	@RequestMapping(method = { RequestMethod.GET })
	public ModelAndView view(HttpServletRequest request, ModelMap model)  {
		model.addAttribute("statTypeList", EquipStatType.getCodeNameStr(emService.getStatTypeList()));
		model.addAttribute("equipCategoryTypeList", EquipType.getCategoryStr(emService.getEquipCategoryTypeList()));
		model.addAttribute("equipSubCategoryTypeList", EquipType.getSubCategoryStr(emService.getEquipSubCategoryTypeList(0)));
		model.addAttribute("equipTypeList", EquipType.getEquipTypeStr(emService.getEquipTypeList(0)));

		return new ModelAndView("balance/equipVirtual");
	}	
	
}