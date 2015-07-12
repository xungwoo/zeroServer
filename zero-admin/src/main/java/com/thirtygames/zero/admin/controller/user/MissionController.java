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
import com.thirtygames.zero.admin.validator.MissionValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.model.Mission;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.service.admintool.user.AdmMissionService;

@Slf4j
@Controller
@RequestMapping(value = "/user/mission")
public class MissionController extends AdminGridController<Mission, Integer, AdmMissionService, MissionValidator> {
	@Autowired
	AdmMissionService missionService;
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.MISSION_ACCOUNT_ID,
		DisplayColumnId.MISSION_MISSION_ID,
		DisplayColumnId.MISSION_CURRENT,
		DisplayColumnId.MISSION_REWARD_DONE
	};
	
	private class MissionConverter implements RowConverter<Map<DisplayColumnId, Object>, Mission> {

		@Override
		public Map<DisplayColumnId, Object> convert(Mission mission) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.MISSION, mission);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public MissionController() {
		super("미션관리", "미션관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new MissionConverter());
		pageInfo.setTitle("Mission");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.User);
		adminMenu.setSelectMenu("Mission");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("user/missionList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		return model;
	};	
}
