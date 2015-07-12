package com.thirtygames.zero.admin.controller.log;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.EquipLevelUpLogValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.equipment.EquipLevelUpLog;
import com.thirtygames.zero.common.service.admintool.log.AdmEquipLevelUpLogService;

@Slf4j
@Controller
@RequestMapping(value = "/log/equip/level-up")
public class EquipLevelUpLogController extends AdminGridController<EquipLevelUpLog, String, AdmEquipLevelUpLogService, EquipLevelUpLogValidator> {
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.UNIT_LOG_UNIT_ID,
		DisplayColumnId.UNIT_LOG_UNIT_TYPE,
		DisplayColumnId.UNIT_LOG_ACCOUNT_ID,
		DisplayColumnId.UNIT_LOG_STATUS,
		DisplayColumnId.UNIT_LOG_RESULT_LEVEL,
		DisplayColumnId.UNIT_LOG_LOG_YMDT
	};
	
	//TODO Excel
	private class EquipLevelUpLogConverter implements RowConverter<Map<DisplayColumnId, Object>, EquipLevelUpLog> {
		@Override
		public Map<DisplayColumnId, Object> convert(EquipLevelUpLog log) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.UNIT_LOG, log);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public EquipLevelUpLogController() {
		super("장비강화 로그", "장비강화 로그", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new EquipLevelUpLogConverter());
		pageInfo.setTitle("EquipLevelUpLog");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Log);
		adminMenu.setSelectMenu("EquipLevelUpLog");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("log/equipLevelUpLogList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		
		//model.addAttribute("AdminLogTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(AdminLogType.get$CODE_LOOKUP()));
		return model;
	};	
}
