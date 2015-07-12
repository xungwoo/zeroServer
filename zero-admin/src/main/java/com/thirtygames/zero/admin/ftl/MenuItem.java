package com.thirtygames.zero.admin.ftl;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;

@Data
public class MenuItem {
	public static String TARGET_BLANK = "target=_blank";
	private String id = ""; 
	private String url = "";
	private String label = "";
	private String target = "";
	List<MenuItem> subItems = new ArrayList<MenuItem>();
	
	public MenuItem() { }
	
	/**
	 * @param label
	 * @param id
	 * @param url
	 * @param isTargetBlank
	 */
	public MenuItem( String label, String id, String url, boolean isTargetBlank ){
		this.id = id; 
		this.url = url; 
		this.label = label; 
		this.target = isTargetBlank ? TARGET_BLANK : ""; 
	}
	
	/**
	 * @param label
	 * @param id
	 * @param url
	 */
	public MenuItem( String label, String id, String url){
		this.id = id; 
		this.url = url; 
		this.label = label; 
	}
	
	/**
	 * Link 없는 Category Menu 생성
	 * 
	 * @param category
	 */
	public MenuItem( Category category){
		this.id = category.toString(); 
		this.url = ""; 
		this.label = category.toString(); 
	}

	public void addSubItem( MenuItem item ) {
		subItems.add( item );
	}
	
	public MenuItem getFirstItem() {
		return subItems.get(0);
	}
}
