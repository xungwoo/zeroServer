package com.thirtygames.zero.common.etc.document;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

public class CellHandlerHelper {

	/**
	 * 
	 */
	private Map<CellType, CellHandler> cellHandlers;

	public CellHandlerHelper() {
		cellHandlers = new HashMap<CellType, CellHandler>();
		addDefaultCellHandlers();
	}

	public Map<CellType, CellHandler> getCellHandlers() {
		return cellHandlers;
	}

	private void addDefaultCellHandlers() {
		addHeaderCellHandler();
		addTextCellHandler();
		addNumberCellHandler();
		addDateCellHandler();
		addTimeCellHandler();
		addCurrencyCellHandler();
		addRatioCellHandler();
		addBooleanCellHandler();
	}

	private void addHeaderCellHandler() {
		cellHandlers.put(CellType.HEADER, new CellHandler() {
			@Override
			public void setCellValue(Cell cell, Object value) {
				cell.setCellValue((String)value);
			}
		});
	}

	private void addTextCellHandler() {
		cellHandlers.put(CellType.TEXT, new CellHandler() {
			@Override
			public void setCellValue(Cell cell, Object value) {
				if (value != null) {
					cell.setCellValue(String.valueOf(value));
				}
			}
		});
	}

	private void addNumberCellHandler() {
		cellHandlers.put(CellType.NUMBER, new CellHandler() {
			@Override
			public void setCellValue(Cell cell, Object value) {
				cell.setCellValue(((Number)value).doubleValue());
			}
		});
	}

	private void addDateCellHandler() {
		cellHandlers.put(CellType.DATE, new CellHandler() {
			@Override
			public void setCellValue(Cell cell, Object value) {
				cell.setCellValue((Date)value);
			}
		});
	}

	private void addTimeCellHandler() {
		cellHandlers.put(CellType.TIME, new CellHandler() {
			@Override
			public void setCellValue(Cell cell, Object value) {
				cell.setCellValue((Date)value);
			}
		});
	}

	private void addCurrencyCellHandler() {
		cellHandlers.put(CellType.CURRENCY, new CellHandler() {
			@Override
			public void setCellValue(Cell cell, Object value) {
				cell.setCellValue(((Number)value).doubleValue());
			}
		});
	}

	private void addRatioCellHandler() {
		cellHandlers.put(CellType.RATIO, new CellHandler() {
			@Override
			public void setCellValue(Cell cell, Object value) {
				cell.setCellValue(((Number)value).doubleValue());
			}
		});
	}

	private void addBooleanCellHandler() {
		cellHandlers.put(CellType.BOOLEAN, new CellHandler() {
			@Override
			public void setCellValue(Cell cell, Object value) {
				cell.setCellValue((Boolean)value);
			}
		});
	}
}
