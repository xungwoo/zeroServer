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
import com.thirtygames.zero.admin.validator.ResourceValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.model.admintool.UserResource;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.service.admintool.user.AdmAccountService;
import com.thirtygames.zero.common.service.admintool.user.AdmResourceService;

@Slf4j
@Controller
@RequestMapping(value = "/user/resource")
public class UserResourceController extends AdminGridController<UserResource, String, AdmResourceService, ResourceValidator> {
	
	@Autowired
	AdmAccountService accountService;
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.RESOURCE_ACCOUT_ID,
		DisplayColumnId.RESOURCE_MEMBER_NO,
		DisplayColumnId.RESOURCE_NICK_NAME,
		DisplayColumnId.RESOURCE_GOLD,
		DisplayColumnId.RESOURCE_CASH,
		DisplayColumnId.RESOURCE_AP_LAST_YMDT,
		DisplayColumnId.RESOURCE_AP_LAST_VALUE,
		DisplayColumnId.RESOURCE_AP_MAX,
		DisplayColumnId.RESOURCE_BP_LAST_YMDT,
		DisplayColumnId.RESOURCE_BP_LAST_VALUE,
		DisplayColumnId.RESOURCE_BP_MAX,
		DisplayColumnId.RESOURCE_FP,
		DisplayColumnId.RESOURCE_UNLOCK_KEY,
		DisplayColumnId.RESOURCE_EQUIP_LEVELUP_DRUG,
		DisplayColumnId.RESOURCE_EQUIP_TICKET,
		DisplayColumnId.RESOURCE_GEN_YMDT
	};
	
	private class ResourceRowConverter implements RowConverter<Map<DisplayColumnId, Object>, UserResource> {

		@Override
		public Map<DisplayColumnId, Object> convert(UserResource resource) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.RESOURCE, resource);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public UserResourceController() {
		super("재화", "재화", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new ResourceRowConverter());
		pageInfo.setTitle("Resource");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.User);
		adminMenu.setSelectMenu("Resource");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("user/resourceList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		return model;
	};
}
