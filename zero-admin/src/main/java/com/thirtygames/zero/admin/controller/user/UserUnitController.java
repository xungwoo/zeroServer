package com.thirtygames.zero.admin.controller.user;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.UnitValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.AjaxResponseUtil;
import com.thirtygames.zero.common.etc.util.JacksonUtil;
import com.thirtygames.zero.common.model.admintool.AdminUnit;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.security.AdminUser;
import com.thirtygames.zero.common.service.admintool.CommonCodeService;
import com.thirtygames.zero.common.service.admintool.user.AdmUnitService;

@Slf4j
@Controller
@RequestMapping(value = "/user/unit")
public class UserUnitController extends AdminGridController<AdminUnit, String, AdmUnitService, UnitValidator> {
	@Autowired
	AdmUnitService unitService;
	
	@Autowired
	CommonCodeService commonCodeService;
	
	@ModelAttribute("unitTypeJson")
	public String getUnitTypeList() {
		return JacksonUtil.toJson(commonCodeService.getUnitTypeList());
	}
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.UNIT_UNIT_ID,
		DisplayColumnId.UNIT_ACCOUNT_ID,
		DisplayColumnId.UNIT_UNIT_TYPE,
		DisplayColumnId.UNIT_LEVEL,
		DisplayColumnId.UNIT_LIMIT_EXCEED_END_YMDT,
		DisplayColumnId.UNIT_SKILL_0LV,
		DisplayColumnId.UNIT_SKILL_1LV,
		DisplayColumnId.UNIT_SKILL_2LV,
		DisplayColumnId.UNIT_SKILL_3LV
	};
	
	private class UnitRowConverter implements RowConverter<Map<DisplayColumnId, Object>, AdminUnit> {

		@Override
		public Map<DisplayColumnId, Object> convert(AdminUnit unit) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.UNIT, unit);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public UserUnitController() {
		super("유닛관리", "유닛관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new UnitRowConverter());
		pageInfo.setTitle("Unit");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.User);
		adminMenu.setSelectMenu("Unit");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("user/unitList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		return model;
	};	
	
	
	@RequestMapping(value = "/reset/{shardIndex}", method = { RequestMethod.POST })
	public @ResponseBody
	ModelAndView unitReset(
			@RequestParam(required = true, value = "unitId") String unitId,
			@RequestParam(required = true, value = "accountId") String accountId,
			AdminUser adminUser,
			SessionStatus status, 
			ModelMap model)
			throws RestException {
		dataSourceService.switchDataSource(DataSource.getAccountDS(accountId));
		String resultLog = service.unitReset(unitId, accountId);
		model.addAttribute("resultLog", resultLog);
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		log.debug("resultLog::" + resultLog);
		return new ModelAndView(AjaxResponseUtil.jsonResult(Maps.newHashMap(), model));
	}	
}
