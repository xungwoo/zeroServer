package com.thirtygames.zero.common.etc.document;

import org.apache.poi.ss.usermodel.Cell;

public interface CellHandler {

	/**
	 * 
	 * @param cell
	 * 
	 * @param value
	 */

	void setCellValue(Cell cell, Object value);

}