package com.thirtygames.zero.common.etc.util;

public class PageUtil {
	
	public static int getPageCount(int totalRowCount, int pageSize) {
		int pageCount = (totalRowCount - 1 + pageSize) / pageSize;
		return pageCount;
	}

}
