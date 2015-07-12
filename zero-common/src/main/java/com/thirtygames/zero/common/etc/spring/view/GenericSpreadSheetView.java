package com.thirtygames.zero.common.etc.spring.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.thirtygames.zero.common.etc.document.CellHandler;
import com.thirtygames.zero.common.etc.document.CellType;
import com.thirtygames.zero.common.etc.document.DefaultColumnDescriptor;
import com.thirtygames.zero.common.etc.document.model.WorkbookModel;
import com.thirtygames.zero.common.etc.document.model.WorksheetModel;

public class GenericSpreadSheetView extends AbstractSpreadSheetView {
	/**
	 * @param model
	 * @param workbook
	 * @param cellStyles
	 * @param request
	 * @param response
	 * @throws Exception
	 * @see com.thirtygames.zero.common.etc.spring.view.nbp.nmp.web.spring.view.document.AbstractSpreadSheetView#buildSpreadSheetDocument(java.util.Map, org.apache.poi.ss.usermodel.Workbook, java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void buildSpreadSheetDocument(Map<String, Object> model, Workbook workbook, Map<CellType, CellStyle> cellStyles, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WorkbookModel workbookModel = (WorkbookModel)model.get("workbookModel");
		if (workbookModel == null) {
			throw new IllegalArgumentException("Model에서 'workbookModel' 속성을 찾을 수가 없습니다. (대소문자 주의!!)");
		}

		for (WorksheetModel worksheetModel : workbookModel) {
			List<DefaultColumnDescriptor> descriptors = worksheetModel.getDescriptors();
			List<?> rowEntries = worksheetModel.getRowEntries();
			String sheetName = worksheetModel.getWorksheetName();
			Sheet sheet = StringUtils.isBlank(sheetName) ? workbook.createSheet() : workbook.createSheet(sheetName);
			writeColumnHeaders(sheet, cellStyles, descriptors);
			writeRowEntries(sheet, cellStyles, descriptors, rowEntries);
		}
	}

	private void writeColumnHeaders(Sheet sheet, Map<CellType, CellStyle> cellStyles, List<DefaultColumnDescriptor> descriptors) {
		int columnIndex = 0;
		Row row = sheet.createRow(0);
		CellStyle style = cellStyles.get(CellType.HEADER);
		for (DefaultColumnDescriptor descriptor : descriptors) {
			sheet.setColumnWidth(columnIndex, descriptor.getWidth() * 500);
			Cell cell = row.createCell(columnIndex++);
			cell.setCellStyle(style);
			cell.setCellValue(descriptor.getHeaderName());
		}
	}

	private void writeRowEntries(Sheet sheet, Map<CellType, CellStyle> cellStyles, List<DefaultColumnDescriptor> descriptors, List<?> rowEntries) {
		int rownum = 1;
		for (Object entry : rowEntries) {
			int column = 0;
			Row row = sheet.createRow(rownum++);
			for (DefaultColumnDescriptor colDesc : descriptors) {
				setCellValue(row, column++, cellStyles, entry, colDesc);
			}
		}
	}

	private void setCellValue(Row row, int column, Map<CellType, CellStyle> cellStyles, Object entry, DefaultColumnDescriptor colDesc) {
		CellStyle cellStyle = cellStyles.get(colDesc.getCellType());
		Object value = colDesc.getValue(entry);
		CellHandler cellHandler = colDesc.getCellHandler();
		if (cellHandler != null) {
			setCellValue(row, column, cellStyle, cellHandler, value);
		} else {
			setCellValue(row, column, cellStyle, colDesc.getCellType(), value);
		}
	}
}
