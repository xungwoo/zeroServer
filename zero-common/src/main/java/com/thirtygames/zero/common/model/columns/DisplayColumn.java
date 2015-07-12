package com.thirtygames.zero.common.model.columns;

import com.thirtygames.zero.common.etc.document.CellHandler;
import com.thirtygames.zero.common.etc.document.CellType;

public class DisplayColumn {

	public static enum Align {

		LEFT,

		CENTER,

		RIGHT

	}

	public static class GridData {

		Integer width;

		Align align = Align.LEFT;

		boolean ellipsis = true;

		boolean useFormatter = true;

		boolean escapeHTML = false;

		public Integer getWidth() {

			return width;

		}

		public void setWidth(Integer width) {

			this.width = width;

		}

		public Align getAlign() {

			return align;

		}

		public void setAlign(Align align) {

			this.align = align;

		}

		public boolean isEllipsis() {

			return ellipsis;

		}

		public void setEllipsis(boolean ellipsis) {

			this.ellipsis = ellipsis;

		}

		public boolean isUseFormatter() {

			return useFormatter;

		}

		public void setUseFormatter(boolean useFormatter) {

			this.useFormatter = useFormatter;

		}

		public boolean isEscapeHTML() {

			return escapeHTML;

		}

		public void setEscapeHTML(boolean escapeHTML) {

			this.escapeHTML = escapeHTML;

		}

	}

	public class ExcelData {

		CellType cellType = CellType.TEXT;

		CellHandler cellHandler;

		Integer width;

		public CellType getCellType() {

			return cellType;

		}

		public void setCellType(CellType cellType) {

			this.cellType = cellType;

		}

		public CellHandler getCellHandler() {

			return cellHandler;

		}

		public void setCellHandler(CellHandler cellHandler) {

			this.cellHandler = cellHandler;

		}

		public Integer getWidth() {

			return width;

		}

		public void setWidth(Integer width) {

			this.width = width;

		}

	}

	DisplayColumnId id;

	String title;

	GridData gridData = new GridData();

	ExcelData excelData = new ExcelData();

	public String getTitle() {

		return title;

	}

	public void setTitle(String title) {

		this.title = title;

	}

	public GridData getGridData() {

		return gridData;

	}

	public void setGridData(GridData gridData) {

		this.gridData = gridData;

	}

	public ExcelData getExcelData() {

		return excelData;

	}

	public void setExcelData(ExcelData excelData) {

		this.excelData = excelData;

	}

	public DisplayColumnId getId() {

		return id;

	}

	public void setId(DisplayColumnId id) {

		this.id = id;

	}

}
