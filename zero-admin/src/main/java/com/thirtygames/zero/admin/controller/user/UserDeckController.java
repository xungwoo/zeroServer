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
import com.thirtygames.zero.admin.validator.DeckValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.etc.util.JacksonUtil;
import com.thirtygames.zero.common.model.admintool.DeckGrid;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.service.admintool.CommonCodeService;
import com.thirtygames.zero.common.service.admintool.user.AdmDeckService;

@Slf4j
@Controller
@RequestMapping(value = "/user/deck")
public class UserDeckController extends AdminGridController<DeckGrid, String, AdmDeckService, DeckValidator> {
	@Autowired
	AdmDeckService deckService;
	
	@Autowired
	CommonCodeService commonCodeService;
	
	@ModelAttribute("unitTypeJson")
	public String getUnitTypeList() {
		return JacksonUtil.toJson(commonCodeService.getUnitTypeList());
	}	
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.DECK_ACCOUNT_ID,
		DisplayColumnId.DECK_TEAM_ID,
		DisplayColumnId.DECK_POSITION_ID,
		DisplayColumnId.DECK_UNIT_ID,
		DisplayColumnId.DECK_UNIT_TYPE,
		DisplayColumnId.DECK_LEVEL,
		DisplayColumnId.DECK_LIMIT_EXCEED_END_YMDT,
		DisplayColumnId.DECK_SKILL_0LV,
		DisplayColumnId.DECK_SKILL_1LV,
		DisplayColumnId.DECK_SKILL_2LV,
		DisplayColumnId.DECK_SKILL_3LV
	};
	
	private class DeckRowConverter implements RowConverter<Map<DisplayColumnId, Object>, DeckGrid> {

		@Override
		public Map<DisplayColumnId, Object> convert(DeckGrid deck) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.DECK_GRID, deck);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public UserDeckController() {
		super("덱관리", "덱관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new DeckRowConverter());
		pageInfo.setTitle("Deck");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.User);
		adminMenu.setSelectMenu("Deck");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("user/deckList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		return model;
	};	
}
