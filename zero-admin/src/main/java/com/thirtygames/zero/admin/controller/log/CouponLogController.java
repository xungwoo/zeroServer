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
import com.thirtygames.zero.admin.validator.CouponLogValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.log.CouponLog;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.admintool.log.AdmCouponLogService;

@Slf4j
@Controller
@RequestMapping(value = "/log/coupon")
public class CouponLogController extends AdminGridController<CouponLog, String, AdmCouponLogService, CouponLogValidator> {
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.UNIT_LOG_UNIT_ID,
		DisplayColumnId.UNIT_LOG_UNIT_TYPE,
		DisplayColumnId.UNIT_LOG_ACCOUNT_ID,
		DisplayColumnId.UNIT_LOG_STATUS,
		DisplayColumnId.UNIT_LOG_RESULT_LEVEL,
		DisplayColumnId.UNIT_LOG_LOG_YMDT
	};
	
	//TODO Excel
	private class CouponLogConverter implements RowConverter<Map<DisplayColumnId, Object>, CouponLog> {
		@Override
		public Map<DisplayColumnId, Object> convert(CouponLog couponLog) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.UNIT_LOG, couponLog);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public CouponLogController() {
		super("쿠폰 로그", "쿠폰 로그", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new CouponLogConverter());
		pageInfo.setTitle("CouponLog");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Log);
		adminMenu.setSelectMenu("CouponLog");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("log/couponLogList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		
		model.addAttribute("RewardCategoryOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardCategory.get$CODE_LOOKUP()));
		model.addAttribute("RewardTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardType.get$CODE_LOOKUP()));
		model.addAttribute("RewasonTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.ReasonType.get$CODE_LOOKUP()));
		return model;
	};	
}
