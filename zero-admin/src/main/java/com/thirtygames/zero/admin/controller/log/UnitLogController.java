package com.thirtygames.zero.admin.controller.log;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.UnitLogValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.etc.util.JacksonUtil;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.log.UnitLog;
import com.thirtygames.zero.common.model.log.UnitLog.UnitLogStatus;
import com.thirtygames.zero.common.service.admintool.CommonCodeService;
import com.thirtygames.zero.common.service.admintool.log.AdmUnitLogService;

@Slf4j
@Controller
@RequestMapping(value = "/log/unit")
public class UnitLogController extends AdminGridController<UnitLog, Integer, AdmUnitLogService, UnitLogValidator> {
	@Autowired
	AdmUnitLogService unitLogService;
	
	@Autowired
	CommonCodeService commonCodeService;
	
	@ModelAttribute("unitTypeJson")
	public String getUnitTypeList() {
		return JacksonUtil.toJson(commonCodeService.getUnitTypeList());
	}
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.UNIT_LOG_UNIT_ID,
		DisplayColumnId.UNIT_LOG_UNIT_TYPE,
		DisplayColumnId.UNIT_LOG_ACCOUNT_ID,
		DisplayColumnId.UNIT_LOG_STATUS,
		DisplayColumnId.UNIT_LOG_RESULT_LEVEL,
		DisplayColumnId.UNIT_LOG_LOG_YMDT
	};
	
	private class UnitLogConverter implements RowConverter<Map<DisplayColumnId, Object>, UnitLog> {

		@Override
		public Map<DisplayColumnId, Object> convert(UnitLog unitLog) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.UNIT_LOG, unitLog);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public UnitLogController() {
		super("유닛로그관리", "유닛로그관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new UnitLogConverter());
		pageInfo.setTitle("UnitLog");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Log);
		adminMenu.setSelectMenu("UnitLog");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("log/unitLogList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		model.addAttribute("UnitLogStatusOpts", Joiner.on(";").withKeyValueSeparator(":").join(UnitLogStatus.get$CODE_LOOKUP()));
		return model;
	};	
}
