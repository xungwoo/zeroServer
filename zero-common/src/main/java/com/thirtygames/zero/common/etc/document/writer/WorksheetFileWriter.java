package com.thirtygames.zero.common.etc.document.writer;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Lists;
import com.thirtygames.zero.common.etc.document.CellHandler;
import com.thirtygames.zero.common.etc.document.CellHandlerHelper;
import com.thirtygames.zero.common.etc.document.CellStyleHelper;
import com.thirtygames.zero.common.etc.document.CellType;
import com.thirtygames.zero.common.etc.document.ColumnDescriptor;
import com.thirtygames.zero.common.etc.document.model.WorkbookModel;

public class WorksheetFileWriter implements SheetWriter {
	public static final String XML_ENCODING = "UTF-8";

	private Map<CellType, CellHandler> cellHandlers;

	private Map<CellType, CellStyle> cellStyles;

	private List<ColumnDescriptor> descriptors;

	private RowWriter rowWriter;

	private WorkbookFile workbookFile;

	private String fileDescriptorId;

	public WorksheetFileWriter(WorkbookModel workbookModel, List<ColumnDescriptor> descriptors, String sheetName) {
		this(workbookModel, descriptors, sheetName, "");
	}

	public WorksheetFileWriter(WorkbookModel workbookModel, List<ColumnDescriptor> descriptors, String sheetName, String fileDescriptorId) {
		this.descriptors = descriptors;
		this.fileDescriptorId = fileDescriptorId;
		workbookModel.addWorksheetWriter(this);
		createWorkbookFile(workbookModel.getWorkbook(), sheetName);
		workbookModel.setWorkbookFile(workbookFile);
		writeColumnHeaders();
	}

	/**
	 * <ol>
	 * 	<li>create a template workbook, create sheets and global objects such as cell styles, number formats, etc.</li>
	 * 	<li>create an application that streams data in a text file.</li>
	 * </ol>
	 * 
	 * @param sheetName
	 */
	private void createWorkbookFile(Workbook workbook, String sheetName) {
		if (!(workbook instanceof XSSFWorkbook)) {
			throw new IllegalArgumentException("wrong workbook type!!");
		}

		cellStyles = new CellStyleHelper().getCellStyles(workbook);
		cellHandlers = new CellHandlerHelper().getCellHandlers();
		workbookFile = new WorkbookFile(((XSSFSheet)workbook.createSheet(sheetName)).getPackagePart().getPartName().getName(), fileDescriptorId);

		try {
			OutputStream os = new FileOutputStream(workbookFile.getWorkbook());
			workbook.write(os);
			os.close();
			rowWriter = new RowWriter(new FileOutputStream(workbookFile.getSheet()), XML_ENCODING);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void writeColumnHeaders() {
		List<Integer> length = Lists.newArrayList();
		for (ColumnDescriptor columnDescriptor : descriptors) {
			length.add(columnDescriptor.getWidth());
		}
		rowWriter.beginSheet(length);
		rowWriter.insertRow(0);
		int styleIndex = cellStyles.get(CellType.HEADER).getIndex();
		int column = 0;
		for (ColumnDescriptor descriptor : descriptors) {
			rowWriter.createCell(column++, descriptor.getHeaderName(), styleIndex);
		}
		rowWriter.endRow();
	}

	public void writeRowEntries(List<?> rowEntries) {
		int rownum = 1;
		for (Object entry : rowEntries) {
			writeRowEntry(rownum++, entry);
		}
	}

	public void writeRowEntry(final int rownum, Object entry) {
		int column = 0;
		rowWriter.insertRow(rownum);
		for (ColumnDescriptor descriptor : descriptors) {
			setCellValue(column++, entry, descriptor);
		}
		rowWriter.endRow();
	}

	private void setCellValue(final int column, Object entry, ColumnDescriptor colDesc) {
		CellType cellType = colDesc.getCellType();
		CellHandler cellHandler = colDesc.getCellHandler();
		if (cellHandler == null) {
			cellHandler = cellHandlers.get(cellType);
		}
		setCellValue(column, cellStyles.get(cellType), cellHandler, colDesc.getValue(entry));
	}

	private void setCellValue(final int column, CellStyle cellStyle, CellHandler cellHandler, Object value) {
		Cell cell = new CellWriter(rowWriter, column);
		cell.setCellStyle(cellStyle);
		if (value != null && cellHandler != null) {
			cellHandler.setCellValue(cell, value);
		}
	}

	public WorkbookFile getWorkbookFile() {
		return workbookFile;
	}

	/**
	 * 
	 * @see com.thirtygames.zero.common.etc.document.writer.nbp.nmp.order.service.component.document.writer.SheetWriter#closeSheet()
	 */
	@Override
	public void closeSheet() {
		rowWriter.endSheet();
		rowWriter.closeSilently();
		//		workbookFile.closeWorkbookArchive();
	}
}
