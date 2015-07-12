package com.thirtygames.zero.admin.controller.ladder;

import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.model.GridJsonResult;
import com.thirtygames.zero.admin.validator.LadderValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.PageUtil;
import com.thirtygames.zero.common.model.Ladder;
import com.thirtygames.zero.common.model.LadderMatch;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.params.grid.GridFilter;
import com.thirtygames.zero.common.service.admintool.log.AdmLadderMatchLogService;
import com.thirtygames.zero.common.service.admintool.user.AdmLadderService;

@Slf4j
@Controller
@RequestMapping(value = "/ladder/account")
public class LadderController extends AdminGridController<Ladder, String, AdmLadderService, LadderValidator> {
	
	@Autowired
	AdmLadderService ladderService;
	
	@Autowired
	AdmLadderMatchLogService ladderMatchLogService;
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.LADDER_ACCOUNT_ID,
		DisplayColumnId.LADDER_LEAGUE,
		DisplayColumnId.LADDER_LADDER,
		DisplayColumnId.LADDER_WIN,
		DisplayColumnId.LADDER_LOSE,
		DisplayColumnId.LADDER_IS_PREV_WIN,
		DisplayColumnId.LADDER_WINNING_STREAK_CNT,
		DisplayColumnId.LADDER_WINNING_STREAK_MAX,
		DisplayColumnId.LADDER_LAST_GAME_TIMESTAMP,
		DisplayColumnId.LADDER_LAST_GAME_NO,
		DisplayColumnId.LADDER_RESET_DATE
	};
	
	private class LadderRowConverter implements RowConverter<Map<DisplayColumnId, Object>, Ladder> {

		@Override
		public Map<DisplayColumnId, Object> convert(Ladder ladder) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.LADDER, ladder);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public LadderController() {
		super("래더관리", "래더관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new LadderRowConverter());
		pageInfo.setTitle("Ladder");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.User);
		adminMenu.setSelectMenu("Ladder");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("ladder/ladderList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		return model;
	};
	
	@RequestMapping(value = "/sub-grid/{shardIndex}", method = { RequestMethod.GET })
	public @ResponseBody
	GridJsonResult<LadderMatch> subGrid(
			@RequestParam(required = false, value = "page", defaultValue = "1") int page,
			@RequestParam(required = false, value = "rows", defaultValue = "20") int rows,
			@RequestParam(required = true, value = "key") String key, 
			@RequestParam(required = false, value = "sidx") String sidx,
		    @RequestParam(required = false, value = "sord") String sord,	
		    @RequestParam(required = false, value = "filters") String filters,		
		    @RequestParam(required = false, value = "_search") Boolean search,	
		    @PathVariable Integer shardIndex,
			ModelMap model)
			throws RestException {
		dataSourceService.switchDataSource(DataSource.findByCode(shardIndex));
		
		LadderMatch ladderMatch = new LadderMatch();
		ladderMatch.setAccountId(key);
		
		if (search != null && search) {
			GridFilter gridFilter = this.getJsonObjectMapper(filters);
			ladderMatch.setGroupOp(gridFilter.getGroupOp());
			ladderMatch.setRules(gridFilter.getRules());
		}
		
		int start = (page - 1) * rows;
		List<LadderMatch> entityList = ladderMatchLogService.search(start, rows, sidx, sord, ladderMatch);
		
		int count = ladderMatchLogService.size(ladderMatch);
		int total = PageUtil.getPageCount(count, rows);
		
		GridJsonResult<LadderMatch> result = new GridJsonResult<LadderMatch>();	
		result.setRows(entityList);
		result.setPage(page);
		result.setTotal(total);
		result.setRecords(rows);
		return result;
	}	
	
}
