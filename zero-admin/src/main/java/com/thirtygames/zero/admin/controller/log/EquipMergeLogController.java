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
import com.thirtygames.zero.admin.validator.EquipMergeLogValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.equipment.EquipMergeLog;
import com.thirtygames.zero.common.service.admintool.log.AdmEquipMergeLogService;

@Slf4j
@Controller
@RequestMapping(value = "/log/equip/merge")
public class EquipMergeLogController extends AdminGridController<EquipMergeLog, String, AdmEquipMergeLogService, EquipMergeLogValidator> {
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.UNIT_LOG_UNIT_ID,
		DisplayColumnId.UNIT_LOG_UNIT_TYPE,
		DisplayColumnId.UNIT_LOG_ACCOUNT_ID,
		DisplayColumnId.UNIT_LOG_STATUS,
		DisplayColumnId.UNIT_LOG_RESULT_LEVEL,
		DisplayColumnId.UNIT_LOG_LOG_YMDT
	};
	
	//TODO Excel
	private class EquipMergeLogConverter implements RowConverter<Map<DisplayColumnId, Object>, EquipMergeLog> {
		@Override
		public Map<DisplayColumnId, Object> convert(EquipMergeLog log) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.UNIT_LOG, log);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public EquipMergeLogController() {
		super("장비합성 로그", "장비합성 로그", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new EquipMergeLogConverter());
		pageInfo.setTitle("EquipMergeLog");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Log);
		adminMenu.setSelectMenu("EquipMergeLog");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("log/equipMergeLogList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		
		//model.addAttribute("AdminLogTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(AdminLogType.get$CODE_LOOKUP()));
		return model;
	};	
}
