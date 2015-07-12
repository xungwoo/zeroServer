package com.thirtygames.zero.admin.controller.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.model.GridJsonResult;
import com.thirtygames.zero.admin.validator.LadderValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.JacksonUtil;
import com.thirtygames.zero.common.etc.util.PageUtil;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.log.StageLog;
import com.thirtygames.zero.common.service.admintool.CommonCodeService;
import com.thirtygames.zero.common.service.admintool.log.AdmStageLogService;
import com.thirtygames.zero.common.service.admintool.user.AdmEquipmentService;

@Slf4j
@Controller
@RequestMapping(value = "/log/stage")
public class StageLogController extends AdminGridController<StageLog, String, AdmStageLogService, LadderValidator> {
	
	@Autowired
	AdmStageLogService stageLogService;
	
	@Autowired
	AdmEquipmentService equipmentService;
	
	@Autowired
	CommonCodeService commonCodeService;
	
	@ModelAttribute("categoryListJson")
	public String getCategoryList() {
		return JacksonUtil.toJson(commonCodeService.getCategoryList());
	}
	
	@ModelAttribute("subCategoryListJson")
	public String getSubCategoryList() {
		return JacksonUtil.toJson(commonCodeService.getSubCategoryList());
	}
	
	@ModelAttribute("equipTypeListJson")
	public String getEquipTypeList() {
		return JacksonUtil.toJson(commonCodeService.getEquipNameList());
	}
	
	@ModelAttribute("equipStatTypeListJson")
	public String getEquipStatTypeList() {
		return JacksonUtil.toJson(commonCodeService.getEquipStatList());
	}
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.STAGE_LOG_ACCOUNT_ID,
		DisplayColumnId.STAGE_LOG_DECK,
		DisplayColumnId.STAGE_LOG_CLEAR_MODE,
		DisplayColumnId.STAGE_LOG_PLAY_TIME,
		DisplayColumnId.STAGE_LOG_GAIN_GOLD,
		DisplayColumnId.STAGE_LOG_IS_WIN,
		DisplayColumnId.STAGE_LOG_LOG_YMDT
	};
	
	private class StageLogRowConverter implements RowConverter<Map<DisplayColumnId, Object>, StageLog> {

		@Override
		public Map<DisplayColumnId, Object> convert(StageLog stageLog) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.STAGE_LOG, stageLog);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public StageLogController() {
		super("스테이지로그관리", "스테이지로그관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new StageLogRowConverter());
		pageInfo.setTitle("StageLog");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Log);
		adminMenu.setSelectMenu("StageLog");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("log/stageLogList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		return model;
	};
	
	protected GridJsonResult<StageLog> postGridData(GridJsonResult<StageLog> result, ModelMap model) {
		List<StageLog> logList = result.getRows();
		for(StageLog log : logList) {
			List<String> unitTypeList = new ArrayList<String>();
			List<String> unitInfoList = Splitter.on(",").splitToList(log.getDeck());
			for(String unitInfo : unitInfoList ) {
				List<String> infoList = Splitter.on(":").splitToList(unitInfo);
				unitTypeList.add(infoList.get(1));
			}
			
			log.setUnitTypeList(unitTypeList);
		}		
		return result;
	}
	
	@RequestMapping(value = "/equipment/grid/{shardIndex}/{logKey}", method = { RequestMethod.GET })
	public ModelAndView grid(@PathVariable Integer shardIndex, @PathVariable Integer logKey, HttpServletRequest request, ModelMap model)  {
		model.addAttribute("shardIndex", shardIndex);
		model.addAttribute("logKey", logKey);
		return new ModelAndView("log/stageEquipListPopup");
	}
	
	@RequestMapping(value = "/equipment/grid/{shardIndex}/{logKey}", method = { RequestMethod.POST })
	public @ResponseBody
	GridJsonResult<Equipment> subGrid(
			@RequestParam(required = false, value = "page", defaultValue = "1") int page,
			@RequestParam(required = false, value = "rows", defaultValue = "20") int rows,
			@RequestParam(required = false, value = "sidx") String sidx,
		    @RequestParam(required = false, value = "sord") String sord,	
		    @PathVariable Integer shardIndex,
		    @PathVariable Integer logKey,
			ModelMap model)
			throws RestException {
		dataSourceService.switchDataSource(DataSource.findByCode(shardIndex));
		
		model.addAttribute("shardIndex", shardIndex);
		model.addAttribute("logKey", logKey);
		
		StageLog stageLog = stageLogService.get(String.valueOf(logKey));
		
		List<Equipment> entityList = Lists.newArrayList();
		if (stageLog.getGainEquipmentList() != null && stageLog.getGainEquipmentList().size() > 0) {
			entityList = equipmentService.getList(stageLog.getGainEquipmentList());
		}

		GridJsonResult<Equipment> result = new GridJsonResult<Equipment>();	
		result.setRows(entityList);
		result.setPage(page);
		result.setTotal(PageUtil.getPageCount(entityList.size(), rows));
		result.setRecords(rows);
		return result;
	}	
	
}
