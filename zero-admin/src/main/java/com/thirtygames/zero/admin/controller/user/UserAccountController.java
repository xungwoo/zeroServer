package com.thirtygames.zero.admin.controller.user;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.AccountValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.model.admintool.UserAccount;
import com.thirtygames.zero.common.model.admintool.UserAccount.Title;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.service.admintool.user.AdmAccountService;

@Slf4j
@Controller
@RequestMapping(value = "/user/account")
public class UserAccountController extends AdminGridController<UserAccount, String, AdmAccountService, AccountValidator> {
	
	@Autowired
	AdmAccountService accountService;
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.ACCOUNT_ACCOUT_ID,
		DisplayColumnId.ACCOUNT_MEMBER_NO,
		DisplayColumnId.ACCOUNT_NICK_NAME,
		DisplayColumnId.ACCOUNT_CHANNEL_ID,
		DisplayColumnId.ACCOUNT_CHANNEL_TYPE,
		DisplayColumnId.ACCOUNT_PROFILE_URL,
		DisplayColumnId.ACCOUNT_TITLE,
		DisplayColumnId.ACCOUNT_MAX_CLEAR_STAGE,
		DisplayColumnId.ACCOUNT_MAX_CLEAR_MODE,
		DisplayColumnId.ACCOUNT_TUTORIAL,
		DisplayColumnId.ACCOUNT_AUTH_TOKEN,
		DisplayColumnId.ACCOUNT_AUTH_TOKEN_VALID_YMDT,
		DisplayColumnId.ACCOUNT_LANGUAGE,
		DisplayColumnId.ACCOUNT_COUNTRY,
		DisplayColumnId.ACCOUNT_LOCAL_TIME_ZONE,
		DisplayColumnId.ACCOUNT_IS_BLOCK,
		DisplayColumnId.ACCOUNT_SEND_LOG,
		DisplayColumnId.ACCOUNT_LAST_SYNC_YMDT,
		DisplayColumnId.ACCOUNT_MOD_YMDT
	};
	
	private class AccountRowConverter implements RowConverter<Map<DisplayColumnId, Object>, UserAccount> {

		@Override
		public Map<DisplayColumnId, Object> convert(UserAccount account) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.ACCOUNT, account);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public UserAccountController() {
		super("계정관리", "계정관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new AccountRowConverter());
		pageInfo.setTitle("Account");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.User);
		adminMenu.setSelectMenu("Account");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("user/accountList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		
		model.addAttribute("titleOpts", Joiner.on(";").withKeyValueSeparator(":").join(Title.get$CODE_LOOKUP()));
		return model;
	};
}
