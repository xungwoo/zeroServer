package com.thirtygames.zero.admin.controller.balance.equip;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.google.common.base.Joiner;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.model.GridJsonResult;
import com.thirtygames.zero.admin.validator.EquipMetaValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatType;
import com.thirtygames.zero.common.model.equipment.meta.EquipType;
import com.thirtygames.zero.common.model.equipment.meta.EquipmentMeta;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.admintool.balance.AdmEquipMetaService;
import com.thirtygames.zero.common.service.equipment.EquipmentService;

@Slf4j
@Controller
@RequestMapping(value = "/balance/equipment")
public class EquipMetaController extends AdminGridController<EquipmentMeta, String, AdmEquipMetaService, EquipMetaValidator> {
	@Autowired
	EquipmentService eqService;
	
	public EquipMetaController() {
		pageInfo.setTitle("EquipmentMeta");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.BalanceEquip);
		adminMenu.setSelectMenu("EquipmentMeta");
		return adminMenu;
	}
	
	@RequestMapping(value = "/sub-category/{category}", method = { RequestMethod.GET })
	public @ResponseBody String subCategory(@PathVariable(value = "category") int category)  {
		String subCategory = EquipType.getSubCategoryOptions(service.getEquipSubCategoryTypeList(category));
		return subCategory;
	}	
	
	@RequestMapping(value = "/equip-type/{subCategory}", method = { RequestMethod.GET })
	public @ResponseBody String equipType(@PathVariable(value = "subCategory") int subCategory)  {
		String equipType = EquipType.getEquipTypeOptions(service.getEquipTypeList(subCategory));
		return equipType;
	}	
	
	
	protected ModelMap postGrid(ModelMap model) {
		model.addAttribute("equipCategoryTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardType.get$CODE_LOOKUP()));
		
		model.addAttribute("statTypeList", EquipStatType.getCodeNameStr(service.getStatTypeList()));
		model.addAttribute("equipCategoryTypeList", EquipType.getCategoryStr(service.getEquipCategoryTypeList()));
		model.addAttribute("equipSubCategoryTypeList", EquipType.getSubCategoryStr(service.getEquipSubCategoryTypeList(0)));
		model.addAttribute("equipTypeList", EquipType.getEquipTypeStr(service.getEquipTypeList(0)));
		model.addAttribute("gemStatTypeList", EquipType.getGemStatTypeStr(service.getGemStatTypeList()));
		super.setViewName("balance/equipmentMetaList");
		
		model.addAttribute("baseUrl", "/balance/equipment");
		return model;
	};	
	
	
	@RequestMapping(value = "/sub-grid", method = { RequestMethod.GET })
	public @ResponseBody
	GridJsonResult<EquipStatMeta> subGrid(
			@RequestParam(required = true, value = "key") String key, 
			@RequestParam(required = false, value = "sidx") String sidx,
		    @RequestParam(required = false, value = "sord") String sord,				
			ModelMap model)
			throws RestException {

		EquipStatMeta equipStatMeta = new EquipStatMeta();
		equipStatMeta.setEquipMetaKey(Integer.parseInt(key));
		List<EquipStatMeta> entityList = service.getAdminStatMetaList(sidx, sord, equipStatMeta);
		
		GridJsonResult<EquipStatMeta> result = new GridJsonResult<EquipStatMeta>();	
		result.setRows(entityList);
		result.setPage(pageInfo.getPage());
		result.setTotal(pageInfo.getCount());
		result.setRecords(pageInfo.getPageSize());
		return result;
	}	
	
	
	@RequestMapping(value = "/sub-grid/edit", method = { RequestMethod.POST })
	public @ResponseBody
	GridJsonResult<EquipStatMeta> gridEdit(
			@RequestParam(required = true, value="oper") String oper,
			@RequestParam(required = false, value = "key") String key,			
			@RequestParam(required = false, value = "id") String id,			
			@ModelAttribute EquipStatMeta statMeta, 
			BindingResult bindingResult, 
			SessionStatus status, 
			ModelMap model)
			throws RestException {
		
		GridJsonResult<EquipStatMeta> result = new GridJsonResult<EquipStatMeta>();	
		
		if ("add".equals(oper)) {
			if (key == null) throw new RestException("Invalid.Param");
			statMeta.setEquipMetaKey(Integer.parseInt(key));
			service.saveStat(statMeta);
		} else if ("edit".equals(oper)) {
			service.updateStat(statMeta);
		}  else if ("del".equals(oper)) {
			if (id == null) throw new RestException("Invalid.Param");
			service.deleteStat(id);
		}
		
		return result;
	}		
	
	
}