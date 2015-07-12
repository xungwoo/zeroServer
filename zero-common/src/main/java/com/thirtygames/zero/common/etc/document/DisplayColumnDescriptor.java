package com.thirtygames.zero.common.etc.document;

import java.util.Map;

import com.thirtygames.zero.common.model.columns.DisplayColumn;
import com.thirtygames.zero.common.model.columns.DisplayColumn.ExcelData;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;

public class DisplayColumnDescriptor implements ColumnDescriptor {

	/**

	 * 

	 */

	private DisplayColumn displayColumn;

	private DisplayColumnId displayColumnId;

	private ExcelData excelData;

	/**
	 * 
	 * @param displayColumn
	 */

	public DisplayColumnDescriptor(DisplayColumn displayColumn) {

		assert displayColumn != null && displayColumn.getId() != null
				&& displayColumn.getExcelData() != null;

		this.displayColumn = displayColumn;

		this.displayColumnId = displayColumn.getId();

		this.excelData = displayColumn.getExcelData();

	}

	/**
	 * 
	 * @return
	 * 
	 * @see com.thirtygames.zero.common.etc.document.nbp.nmp.order.service.component.document.ColumnDescriptor#getHeaderName()
	 */

	@Override
	public String getHeaderName() {

		return displayColumn.getTitle();

	}

	/**
	 * 
	 * @return
	 * 
	 * @see com.thirtygames.zero.common.etc.document.nbp.nmp.order.service.component.document.ColumnDescriptor#getCellType()
	 */

	@Override
	public CellType getCellType() {

		return excelData.getCellType();

	}

	/**
	 * 
	 * @return
	 * 
	 * @see com.thirtygames.zero.common.etc.document.nbp.nmp.order.service.component.document.ColumnDescriptor#getCellHandler()
	 */

	@Override
	public CellHandler getCellHandler() {

		return excelData.getCellHandler();

	}

	/**
	 * 
	 * @param root
	 * 
	 * @return
	 * 
	 * @see com.thirtygames.zero.common.etc.document.nbp.nmp.order.service.component.document.ColumnDescriptor#getValue(java.lang.Object)
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Object getValue(Object root) {

		return ((Map<DisplayColumnId, Object>) root).get(displayColumnId);

	}

	/**
	 * 
	 * @return
	 * 
	 * @see com.thirtygames.zero.common.etc.document.nbp.nmp.order.service.component.document.ColumnDescriptor#getWidth()
	 */

	@Override
	public int getWidth() {

		return excelData.getWidth();

	}

	public String getDisplayColumnIdName() {

		if (displayColumnId != null) {

			return displayColumnId.name();

		}

		return null;

	}

}
