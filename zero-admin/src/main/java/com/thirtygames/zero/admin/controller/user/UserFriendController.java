package com.thirtygames.zero.admin.controller.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.model.GridJsonResult;
import com.thirtygames.zero.admin.validator.FriendValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.AjaxResponseUtil;
import com.thirtygames.zero.common.etc.util.PageUtil;
import com.thirtygames.zero.common.model.admintool.AdminFriend;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.params.grid.FilterRules;
import com.thirtygames.zero.common.model.params.grid.GridFilter;
import com.thirtygames.zero.common.service.admintool.user.AdmFriendService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;

@Slf4j
@Controller
@RequestMapping(value = "/user/friend")
public class UserFriendController extends AdminGridController<AdminFriend, String, AdmFriendService, FriendValidator> {
	
	@Autowired
	DataSourceService dsManager;
	
	@Autowired
	AdmFriendService friendService;
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.FRIEND_FRIEND_ACCOUNT_KEY,
		DisplayColumnId.FRIEND_FRIEND_ID,
		DisplayColumnId.FRIEND_LAST_HELP_YMDT,
		DisplayColumnId.FRIEND_DECK,
		DisplayColumnId.FRIEND_MAX_CLEAR_STAGE,
		DisplayColumnId.FRIEND_LEAGUE,
		DisplayColumnId.FRIEND_LADDER,
		DisplayColumnId.FRIEND_WIN,
		DisplayColumnId.FRIEND_LOSE,
		DisplayColumnId.FRIEND_WINNING_STREAK_COUNT,
		DisplayColumnId.FRIEND_WINNING_STREAK_MAX,
		DisplayColumnId.FRIEND_TITLE,
		DisplayColumnId.FRIEND_NICKNAME,
		DisplayColumnId.FRIEND_PROFILE_URL
	};
	
	private class FriendRowConverter implements RowConverter<Map<DisplayColumnId, Object>, AdminFriend> {

		@Override
		public Map<DisplayColumnId, Object> convert(AdminFriend friend) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.FRIEND, friend);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public UserFriendController() {
		super("친구관리", "친구관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new FriendRowConverter());
		pageInfo.setTitle("Friend");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.User);
		adminMenu.setSelectMenu("Friend");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("user/friendList");
		return model;
	};
	
	@RequestMapping(value = "/grid", method = { RequestMethod.GET })
	public ModelAndView grid(
			@RequestParam(required = false, value = "page", defaultValue = "1") int page,
			@RequestParam(required = false, value = "rows", defaultValue = "20") int rows,
			HttpServletRequest request, ModelMap model)  {

		super.setViewName("user/friendList");
		return new ModelAndView(getViewName());
	}
	
	@RequestMapping(value = "/grid", method = { RequestMethod.POST })
	public @ResponseBody
	GridJsonResult<AdminFriend> gridData(
			@RequestParam(required = false, value = "page", defaultValue = "1") int page,
			@RequestParam(required = false, value = "rows", defaultValue = "20") int rows,
			@RequestParam(required = false, value = "sidx") String sidx,
		    @RequestParam(required = false, value = "sord") String sord,		
		    @RequestParam(required = false, value = "filters") String filters,		
		    @RequestParam(required = false, value = "_search") Boolean search,	
			@ModelAttribute AdminFriend entity, 
			BindingResult bindingResult, 
			SessionStatus status, 
			ModelMap model)
			throws RestException {
		if (search != null && search) {
			GridFilter gridFilter = this.getJsonObjectMapper(filters);
			entity.setGroupOp(gridFilter.getGroupOp());
			entity.setRules(gridFilter.getRules());
		} else {
			return new GridJsonResult<AdminFriend>();
		}
		
		for (FilterRules rule : entity.getRules()) {
			if (StringUtils.equals("accountId", rule.getField())) {
				entity.setAccountId(rule.getData());
			}
		}
		
		// validator.validate(model, bindingResult);

		int start = (page - 1) * rows;
		List<AdminFriend> entityList = Lists.newArrayList();
		try {
			entityList = getFriends(entity.getAccountId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int count = service.size(entity);
		int total = PageUtil.getPageCount(count, rows);
		
		GridJsonResult<AdminFriend> result = new GridJsonResult<AdminFriend>();	
		result.setRows(entityList);
		result.setPage(page);
		result.setTotal(total);
		result.setRecords(rows);
		return result;
	}
	

	
	@RequestMapping(value = "/grid/delete", method = { RequestMethod.POST })
	public @ResponseBody
	ModelAndView gridEdit(
			@RequestParam(required = true, value = "accountId") String accountId,
			@RequestParam(required = true, value="friendRelationKey") String friendRelationKey,
			ModelMap model)
			throws RestException {
		
		try {
			dsManager.switchDataSource(DataSource.getAccountDS(accountId));
			service.delete(friendRelationKey);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(AjaxResponseUtil.jsonResultError("ERROR", "알 수 없는 오류가 발생했습니다.", null, model));
		}
		
		return new ModelAndView(AjaxResponseUtil.jsonResult(Maps.newHashMap(), model));
	}	
		
	
	
	public List<AdminFriend> getFriends(String accountId) throws Exception {
		AdminFriend friend = new AdminFriend();
		friend.setAccountId(accountId);

		ApiJsonResult<AdminFriend> result = new ApiJsonResult<AdminFriend>();
		result.setParams(friend);
		
		List<AdminFriend> resultList = new ArrayList<AdminFriend>();
		dsManager.switchDataSource(DataSource.getAccountDS(accountId));
		List<AdminFriend> myShardFriendList = service.getFriendList(accountId);
		resultList.addAll(myShardFriendList);
		
		List<AdminFriend> memberNoList = service.getFriendMemberNoList(accountId);
		
		if (!memberNoList.isEmpty()) {
			for (DataSource dst : DataSource.GAME_SHARDS) {
				if (dst != DataSource.getAccountDS(accountId)) {
					dsManager.switchDataSource(dst);
					List<AdminFriend> friendList = service.getFriendListByMemberNoList(memberNoList);

					Iterator<AdminFriend> itFr = friendList.iterator();
					while (itFr.hasNext()) {
						AdminFriend fr = itFr.next();
						Iterator<AdminFriend> itMember = memberNoList.iterator();
						while (itMember.hasNext()) {
							AdminFriend memberFr = itMember.next();
							if (memberFr.getFriendMemberNo().equals(fr.getMemberNo())) {
								fr.setAccountId(memberFr.getAccountId());
								fr.setFriendRelationKey(memberFr.getFriendRelationKey());
								fr.setLastHelpYmdt(memberFr.getLastHelpYmdt());
								fr.setLastPresentYmdt(memberFr.getLastPresentYmdt());
								fr.setIsFacebookFriend(memberFr.getIsFacebookFriend());
								fr.setAddedYmdt(memberFr.getAddedYmdt());
								itMember.remove();
								break;
							}
						}
					}

					log.debug("flist::" + friendList);
					resultList.addAll(friendList);
				}
			}
		}		
		
		
		return resultList;
	}
}
