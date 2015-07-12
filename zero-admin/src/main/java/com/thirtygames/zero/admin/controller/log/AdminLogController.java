package com.thirtygames.zero.admin.controller.log;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.AdminLogValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.model.admintool.AdminLog;
import com.thirtygames.zero.common.model.admintool.AdminLog.AdminLogType;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.service.admintool.log.AdmAdminLogService;

@Slf4j
@Controller
@RequestMapping(value = "/log/admin")
public class AdminLogController extends AdminGridController<AdminLog, String, AdmAdminLogService, AdminLogValidator> {
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.ADMIN_LOG_LOG_KEY,
		DisplayColumnId.ADMIN_LOG_TYPE,
		DisplayColumnId.ADMIN_LOG_MEMO,
		DisplayColumnId.ADMIN_LOG_DATA_KEY,
		DisplayColumnId.ADMIN_LOG_MOD_ID,
		DisplayColumnId.ADMIN_LOG_MOD_YMDT
	};
	
	//TODO Excel
	private class AdminLogConverter implements RowConverter<Map<DisplayColumnId, Object>, AdminLog> {
		@Override
		public Map<DisplayColumnId, Object> convert(AdminLog adminLog) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.ADMIN_LOG, adminLog);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public AdminLogController() {
		super("Admin 수정사유 관리", "Admin 수정사유 관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new AdminLogConverter());
		pageInfo.setTitle("AdminLog");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Log);
		adminMenu.setSelectMenu("AdminLog");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("log/adminLogList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		
		model.addAttribute("AdminLogTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(AdminLogType.get$CODE_LOOKUP()));
		return model;
	};	
}
