package com.thirtygames.zero.admin.controller.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.thirtygames.zero.admin.ftl.PageInfo;

@Data
@EqualsAndHashCode(callSuper=false)
@SessionAttributes("pageInfo")
public class AdminBaseController {
	public AdminMenu adminMenu = AdminMenu.getInstance();	
	
	@ModelAttribute("adminMenu")
	public AdminMenu adminMenu() {
		return adminMenu;
	}
	
	public PageInfo pageInfo = new PageInfo();	

	@ModelAttribute("pageInfo")
	public PageInfo pageInfo() {
		return pageInfo;
	}
	
	String viewName = "";
	String redirectUrl = "";
}