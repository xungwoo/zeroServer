package com.thirtygames.zero.admin.controller.user;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.form.StageClearForm;
import com.thirtygames.zero.admin.validator.StageClearValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.model.admintool.AdminStageClear;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.service.admintool.user.AdmStageClearService;

@Controller
@RequestMapping(value = "/user/stage-clear")
public class StageClearController extends AdminGridController<AdminStageClear, String, AdmStageClearService, StageClearValidator> {
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.STAGECLEAR_ACCOUNT_ID,
		DisplayColumnId.STAGECLEAR_KEY,
		DisplayColumnId.STAGECLEAR_CLEAR_MODE,
		DisplayColumnId.STAGECLEAR_CLEAR_STEP,
		DisplayColumnId.STAGECLEAR_CLEAR_PROGRESS,
		DisplayColumnId.STAGECLEAR_EXPOSED_SCENES,
		DisplayColumnId.STAGECLEAR_MOD_YMDT
	};
	
	private class StageClearRowConverter implements RowConverter<Map<DisplayColumnId, Object>, AdminStageClear> {

		@Override
		public Map<DisplayColumnId, Object> convert(AdminStageClear stageClear) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.STAGECLEAR, stageClear);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public StageClearController() {
		super("스테이지클리어", "스테이지클리어", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS, new StageClearForm());
		setRowConverter(new StageClearRowConverter());
		pageInfo.setTitle("StageClear");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.User);
		adminMenu.setSelectMenu("StageClear");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("user/stageList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		return model;
	};
}
