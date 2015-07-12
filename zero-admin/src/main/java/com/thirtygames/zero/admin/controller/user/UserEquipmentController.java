package com.thirtygames.zero.admin.controller.user;

import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.form.EquipmentForm;
import com.thirtygames.zero.admin.model.GridJsonResult;
import com.thirtygames.zero.admin.validator.EquipmentValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.JacksonUtil;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;
import com.thirtygames.zero.common.service.admintool.CommonCodeService;
import com.thirtygames.zero.common.service.admintool.user.AdmEquipmentService;
import com.thirtygames.zero.common.service.admintool.user.AdmEquipmentStatService;

@Slf4j
@Controller
@RequestMapping(value = "/user/equipment")
public class UserEquipmentController extends AdminGridController<Equipment, String, AdmEquipmentService, EquipmentValidator> {
	@Autowired
	AdmEquipmentService equipmentService;
	
	@Autowired
	AdmEquipmentStatService equipmentStatService;
	
	@Autowired
	CommonCodeService commonCodeService;
	
	@ModelAttribute("categoryListJson")
	public String getCategoryList() {
		return JacksonUtil.toJson(commonCodeService.getCategoryList());
	}
	
	@ModelAttribute("subCategoryListJson")
	public String getSubCategoryList() {
		return JacksonUtil.toJson(commonCodeService.getSubCategoryList());
	}
	
	@ModelAttribute("equipTypeListJson")
	public String getEquipTypeList() {
		return JacksonUtil.toJson(commonCodeService.getEquipNameList());
	}
	
	@ModelAttribute("equipStatTypeListJson")
	public String getEquipStatTypeList() {
		return JacksonUtil.toJson(commonCodeService.getEquipStatList());
	}
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.EQUIPMENT_ACCOUNT_ID,
		DisplayColumnId.EQUIPMENT_UNIT_ID,
		DisplayColumnId.EQUIPMENT_EQUIPMENT_POSITION,
		DisplayColumnId.EQUIPMENT_EQUIPMENT_TYPE,
		DisplayColumnId.EQUIPMENT_CATEGORY,
		DisplayColumnId.EQUIPMENT_SUBCATEGORY,
		DisplayColumnId.EQUIPMENT_GRADE,
		DisplayColumnId.EQUIPMENT_DECORATION1,
		DisplayColumnId.EQUIPMENT_DECORATION2,
		DisplayColumnId.EQUIPMENT_LEVEL,
		DisplayColumnId.EQUIPMENT_EXP,
		DisplayColumnId.EQUIPMENT_TOTAL_EXP,
		DisplayColumnId.EQUIPMENT_OPEN_SOCKETS,
		DisplayColumnId.EQUIPMENT_MAX_SOCKETS,
		DisplayColumnId.EQUIPMENT_SOCKET1,
		DisplayColumnId.EQUIPMENT_SOCKET2,
		DisplayColumnId.EQUIPMENT_SOCKET3,
		DisplayColumnId.EQUIPMENT_GEN_YMDT
	};
	
	private class EquipmentRowConverter implements RowConverter<Map<DisplayColumnId, Object>, Equipment> {

		@Override
		public Map<DisplayColumnId, Object> convert(Equipment equipment) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.EQUIPMENT, equipment);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public UserEquipmentController() {
		super("장비관리", "장비관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS, new EquipmentForm());
		setRowConverter(new EquipmentRowConverter());
		pageInfo.setTitle("Unit");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.User);
		adminMenu.setSelectMenu("Equipment");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("user/equipmentList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		return model;
	};
	
	@RequestMapping(value = "/sub-grid/{shardIndex}", method = { RequestMethod.GET })
	public @ResponseBody
	GridJsonResult<EquipmentStat> subGrid(
			@RequestParam(required = true, value = "key") String key, 
			@RequestParam(required = false, value = "sidx") String sidx,
		    @RequestParam(required = false, value = "sord") String sord,	
		    @PathVariable Integer shardIndex,
			ModelMap model)
			throws RestException {
		dataSourceService.switchDataSource(DataSource.findByCode(shardIndex));
		
		EquipmentStat equipmentStat = new EquipmentStat();
		equipmentStat.setEquipmentId(key);
		List<EquipmentStat> entityList = equipmentStatService.getEquipmentStatList(sidx, sord, equipmentStat);
		
		GridJsonResult<EquipmentStat> result = new GridJsonResult<EquipmentStat>();	
		result.setRows(entityList);
		result.setPage(pageInfo.getPage());
		result.setTotal(pageInfo.getCount());
		result.setRecords(pageInfo.getPageSize());
		return result;
	}	
	
	
	@RequestMapping(value = "/sub-grid/edit/{shardIndex}", method = { RequestMethod.POST })
	public @ResponseBody
	GridJsonResult<EquipmentStat> gridEdit(
			@RequestParam(required = true, value="oper") String oper,
			@RequestParam(required = false, value = "key") String key,			
			@RequestParam(required = false, value = "id") String id,			
			@ModelAttribute EquipmentStat equipmentStat, 
			@PathVariable Integer shardIndex,
			BindingResult bindingResult, 
			SessionStatus status, 
			ModelMap model)
			throws RestException {
		dataSourceService.switchDataSource(DataSource.findByCode(shardIndex));
		
		GridJsonResult<EquipmentStat> result = new GridJsonResult<EquipmentStat>();	
		
		if ("add".equals(oper)) {
			if (key == null) throw new RestException("Invalid.Param");
			equipmentStat.setEquipmentId(id);
			equipmentStatService.save(equipmentStat);
		} else if ("edit".equals(oper)) {
			equipmentStatService.update(equipmentStat);
		}  else if ("del".equals(oper)) {
			if (id == null) throw new RestException("Invalid.Param");
			equipmentStatService.delete(id);
		}
		
		return result;
	}
}
