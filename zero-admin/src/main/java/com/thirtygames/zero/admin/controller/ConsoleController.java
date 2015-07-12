package com.thirtygames.zero.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thirtygames.zero.admin.controller.common.AdminBaseController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;

@Controller
public class ConsoleController extends AdminBaseController {
	
	public ConsoleController() {
		pageInfo.setTitle("Home");
		pageInfo.setPageSize(20);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Home);
		adminMenu.setSelectMenu("");
		return adminMenu;
	}
	

    @RequestMapping(value = "/home", method = RequestMethod.GET)
	public String mainView() {
		return "home";
	}

}