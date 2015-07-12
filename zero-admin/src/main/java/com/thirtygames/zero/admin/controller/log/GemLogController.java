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
import com.thirtygames.zero.admin.validator.GemLogValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.etc.util.JacksonUtil;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.equipment.EquipLog;
import com.thirtygames.zero.common.model.equipment.EquipLog.EquipLogStatus;
import com.thirtygames.zero.common.service.admintool.CommonCodeService;
import com.thirtygames.zero.common.service.admintool.log.AdmGemLogService;

@Slf4j
@Controller
@RequestMapping(value = "/log/gem")
public class GemLogController extends AdminGridController<EquipLog, Integer, AdmGemLogService, GemLogValidator> {
	@Autowired
	AdmGemLogService gemLogService;
	
	@Autowired
	CommonCodeService commonCodeService;
	
	@ModelAttribute("subCategoryListJson")
	public String getSubCategoryList() {
		return JacksonUtil.toJson(commonCodeService.getSubCategoryList());
	}
	
	@ModelAttribute("equipTypeListJson")
	public String getEquipTypeList() {
		return JacksonUtil.toJson(commonCodeService.getEquipNameList());
	}
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.GEMLOG_KEY,
		DisplayColumnId.GEMLOG_EQUIPMENT_ID,
		DisplayColumnId.GEMLOG_EQUIPMENT_TYPE,
		DisplayColumnId.GEMLOG_ACCOUNT_ID,
		DisplayColumnId.GEMLOG_SUB_CATEGORY,
		DisplayColumnId.GEMLOG_GRADE,
		DisplayColumnId.GEMLOG_STATUS,
		DisplayColumnId.GEMLOG_INSTALLED_EQUIP_ID,
		DisplayColumnId.GEMLOG_INSTALLED_SOCKET_NO,
		DisplayColumnId.GEMLOG_USED_EQ_LIST,
		DisplayColumnId.GEMLOG_LOGYMDT
	};
	
	private class GemLogConverter implements RowConverter<Map<DisplayColumnId, Object>, EquipLog> {

		@Override
		public Map<DisplayColumnId, Object> convert(EquipLog gemLog) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.GEMLOG, gemLog);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public GemLogController() {
		super("젬로그관리", "젬로그관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new GemLogConverter());
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Log);
		adminMenu.setSelectMenu("GemLog");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("log/gemLogList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		model.addAttribute("GemLogStatusOpts", Joiner.on(";").withKeyValueSeparator(":").join(EquipLogStatus.get$CODE_LOOKUP()));
		return model;
	};	
}
