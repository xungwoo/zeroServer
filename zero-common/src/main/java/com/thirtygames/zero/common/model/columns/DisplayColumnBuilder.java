package com.thirtygames.zero.common.model.columns;

import com.thirtygames.zero.common.etc.document.CellType;
import com.thirtygames.zero.common.model.columns.DisplayColumn.Align;

public class DisplayColumnBuilder {

	DisplayColumn displayColumn;

	public DisplayColumnBuilder() {

		displayColumn = new DisplayColumn();

	}

	public static DisplayColumnBuilder create(DisplayColumnId id, String title) {

		DisplayColumnBuilder builder = new DisplayColumnBuilder();

		builder.displayColumn.id = id;

		builder.displayColumn.title = title;

		return builder;

	}

	public DisplayColumnBuilder setGridData(Integer width, Align align) {

		setGridWidth(width);

		setGridAlign(align);

		return this;

	}

	public DisplayColumnBuilder setExcelData(CellType cellType, Integer width) {

		if (cellType != null) {

			displayColumn.excelData.cellType = cellType;

		}

		if (width != null) {

			displayColumn.excelData.width = width;

		}

		return this;

	}

	public DisplayColumnBuilder setGridAlign(Align align) {

		if (align != null) {

			displayColumn.gridData.align = align;

		}

		return this;

	}

	public DisplayColumnBuilder setGridWidth(Integer width) {

		if (width != null) {

			displayColumn.gridData.width = width;

		}

		return this;

	}

	public DisplayColumn getDisplayColumn() {

		return displayColumn;

	}

	public DisplayColumnBuilder setUseFormatter(boolean useFormatter) {

		displayColumn.gridData.useFormatter = useFormatter;

		return this;

	}

	public DisplayColumnBuilder setEscapeHTML(boolean escapeHTML) {

		displayColumn.gridData.escapeHTML = escapeHTML;

		return this;

	}

}