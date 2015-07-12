package com.thirtygames.zero.common.etc.document;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class CellStyleHelper {

	/**
	 * @param workbook
	 * @return
	 */
	public Map<CellType, CellStyle> getCellStyles(Workbook workbook) {
		Map<CellType, CellStyle> cellStyles = new HashMap<CellType, CellStyle>();
		addDefaultCellStyles(workbook, cellStyles);
		return cellStyles;
	}

	private void addDefaultCellStyles(Workbook workbook, Map<CellType, CellStyle> cellStyles) {
		addHeaderCellStyle(workbook, cellStyles);
		addTextCellStyle(workbook, cellStyles);
		addDateCellStyle(workbook, cellStyles);
		addCurrencyCellStyle(workbook, cellStyles);
	}

	private void addHeaderCellStyle(Workbook workbook, Map<CellType, CellStyle> cellStyles) {
		Font headerFont = workbook.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		cellStyles.put(CellType.HEADER, style);
	}

	private void addTextCellStyle(Workbook workbook, Map<CellType, CellStyle> cellStyles) {
	}

	private void addDateCellStyle(Workbook workbook, Map<CellType, CellStyle> cellStyles) {
		DataFormat df = workbook.createDataFormat();
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setWrapText(true);
		style.setDataFormat(df.getFormat("yyyy/mm/dd h:mm"));
		cellStyles.put(CellType.DATE, style);
	}

	private void addCurrencyCellStyle(Workbook workbook, Map<CellType, CellStyle> cellStyles) {
		DataFormat df = workbook.createDataFormat();
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		style.setDataFormat(df.getFormat("\"\\\"#,##0"));
		cellStyles.put(CellType.CURRENCY, style);
	}

}
