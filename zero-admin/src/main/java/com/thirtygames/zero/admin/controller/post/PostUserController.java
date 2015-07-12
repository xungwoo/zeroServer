package com.thirtygames.zero.admin.controller.post;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.form.PostUserForm;
import com.thirtygames.zero.admin.validator.PostValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.model.admintool.AdminPost;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.meta.Reward.RewardCategory;
import com.thirtygames.zero.common.model.meta.Reward.RewardType;
import com.thirtygames.zero.common.service.admintool.AdmPostService;

@Slf4j
@Controller
@RequestMapping(value = "/post/user")
public class PostUserController extends AdminGridController<AdminPost, String, AdmPostService, PostValidator> {
	
	protected static final DisplayColumnId[] displayColumnIds = {
//		DisplayColumnId.MISSION_ACCOUNT_ID,
//		DisplayColumnId.MISSION_MISSION_ID,
//		DisplayColumnId.MISSION_CURRENT,
//		DisplayColumnId.MISSION_REWARD_DONE
	};
	
	public PostUserController() {
		super("우편함관리", "우편함관리", null, AdminGridController.EMPTY_COLUMN_IDS, new PostUserForm());
		pageInfo.setTitle("Post");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Post);
		adminMenu.setSelectMenu("Post");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		model.addAttribute("rewardCategoyList", RewardCategory.getCodeStr());
		model.addAttribute("rewardTypeList", RewardType.getCodeStr());
		
		super.setViewName("post/postUserList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		return model;
	};	
}


