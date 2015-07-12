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
import com.thirtygames.zero.admin.validator.RewardLogValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.model.admintool.AdminRewardLog;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.admintool.log.AdmRewardLogService;

@Slf4j
@Controller
@RequestMapping(value = "/log/reward")
public class RewardLogController extends AdminGridController<AdminRewardLog, String, AdmRewardLogService, RewardLogValidator> {
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.UNIT_LOG_UNIT_ID,
		DisplayColumnId.UNIT_LOG_UNIT_TYPE,
		DisplayColumnId.UNIT_LOG_ACCOUNT_ID,
		DisplayColumnId.UNIT_LOG_STATUS,
		DisplayColumnId.UNIT_LOG_RESULT_LEVEL,
		DisplayColumnId.UNIT_LOG_LOG_YMDT
	};
	
	//TODO Excel
	private class RewardLogConverter implements RowConverter<Map<DisplayColumnId, Object>, AdminRewardLog> {
		@Override
		public Map<DisplayColumnId, Object> convert(AdminRewardLog log) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.UNIT_LOG, log);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public RewardLogController() {
		super("보상 로그", "보상 로그", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new RewardLogConverter());
		pageInfo.setTitle("RewardLog");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Log);
		adminMenu.setSelectMenu("RewardLog");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("log/rewardLogList");
		model.addAttribute("baseUrl", "/log/reward");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		
		model.addAttribute("RewardCategoryOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardCategory.get$CODE_LOOKUP()));
		model.addAttribute("RewardTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardType.get$CODE_LOOKUP()));
		model.addAttribute("ReasonTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.ReasonType.get$CODE_LOOKUP()));
		return model;
	};	
}
