package com.thirtygames.zero.admin.controller.user;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.AchievementValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.model.Achievement;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.service.admintool.user.AdmAchievementService;

@Slf4j
@Controller
@RequestMapping(value = "/user/achievement")
public class AchievementController extends AdminGridController<Achievement, Integer, AdmAchievementService, AchievementValidator> {
	@Autowired
	AdmAchievementService achievementService;
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.ACHIEVEMENT_ACCOUNT_ID,
		DisplayColumnId.ACHIEVEMENT_ACHIEVEMENT_ID,
		DisplayColumnId.ACHIEVEMENT_STEP,
		DisplayColumnId.ACHIEVEMENT_CURRENT,
		DisplayColumnId.ACHIEVEMENT_REWARD_DONE
	};
	
	private class AchievementConverter implements RowConverter<Map<DisplayColumnId, Object>, Achievement> {

		@Override
		public Map<DisplayColumnId, Object> convert(Achievement achievement) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.ACHIEVEMENT, achievement);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public AchievementController() {
		super("업적관리", "업적관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new AchievementConverter());
		pageInfo.setTitle("Achievement");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.User);
		adminMenu.setSelectMenu("Achievement");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("user/achievementList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		return model;
	};	
}
