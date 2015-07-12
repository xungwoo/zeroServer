package com.thirtygames.zero.admin.ftl;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class PageInfo {
	
	String title = "";
	
	List<Integer> pages = new ArrayList<Integer>();
	int page = 1;
	int pageSize = 20;	// Size of list in a page
	int count = 0;		// Total contents row size
	int prevPage;
	int nextPage;
	
	int start = 0;	// from in limit query  
	
	Boolean prevEnable = false;
	Boolean nextEnable = false;
	

	public void setPageInfo(int page) {
		if (page > 0) this.setPage(page);
		
		int startPage = ((this.getPage() - 1) / pageSize * pageSize) + 1;
		
		this.setStart((page * pageSize) - pageSize);
		this.setPrevPage(startPage - 1);
		this.setNextPage(startPage + pageSize);
		this.setPrevEnable(startPage != 1);
	}
	
	public void setTotalPageCount(int count) {
		int totalPageCount = (int) Math.ceil(count / (float) pageSize);
		for (int i = 0; i < 10; i++) {
			int pageIndex = this.getStart() + i;
			pages.add(pageIndex);
			if (pageIndex >= totalPageCount) {
				break;
			}
		}
		this.setNextEnable(this.getStart() + pageSize < totalPageCount);		
	}
	
}
