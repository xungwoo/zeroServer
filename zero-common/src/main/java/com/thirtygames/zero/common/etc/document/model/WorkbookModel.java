package com.thirtygames.zero.common.etc.document.model;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Lists;
import com.thirtygames.zero.common.etc.document.DefaultColumnDescriptor;
import com.thirtygames.zero.common.etc.document.writer.SheetWriter;
import com.thirtygames.zero.common.etc.document.writer.WorkbookFile;

public class WorkbookModel implements Iterable<WorksheetModel> {

	/**

	 * 

	 */

	private WorkbookFile workbookFile;

	/**

	 * 

	 */

	private Workbook workbook;

	private String workbookName;

	private List<WorksheetModel> worksheets = Lists.newArrayList();

	private List<SheetWriter> sheetWriters = Lists.newArrayList();

	private boolean usingAutoSizeColumn = false;

	private String extention;

	public WorkbookModel(String workbookName) {

		this(workbookName, "xlsx");

	}

	public WorkbookModel(String workbookName, String extention) {

		this.workbookName = workbookName;

		this.workbook = StringUtils.equals(extention, "xls") ? new HSSFWorkbook()
				: new XSSFWorkbook();

		this.extention = extention;

	}

	public WorkbookFile getWorkbookFile() {

		return workbookFile;

	}

	public void setWorkbookFile(WorkbookFile workbookFile) {

		this.workbookFile = workbookFile;

	}

	public Workbook getWorkbook() {

		return workbook;

	}

	public String getWorkbookName() {

		return workbookName;

	}

	public void setWorkbookName(String workbookName) {

		this.workbookName = workbookName;

	}

	public boolean isUsingAutoSizeColumn() {

		return usingAutoSizeColumn;

	}

	public void setUsingAutoSizeColumn(boolean usingAutoSizeColumn) {

		this.usingAutoSizeColumn = usingAutoSizeColumn;

	}

	public void addWorksheet(List<DefaultColumnDescriptor> descriptors,
			List<?> rowEntries, String worksheetName) {

		addWorksheet(new WorksheetModel(descriptors, rowEntries, worksheetName));

	}

	public void addWorksheet(WorksheetModel worksheet) {

		worksheets.add(worksheet);

	}

	public void addWorksheetWriter(SheetWriter sheetWriter) {

		sheetWriters.add(sheetWriter);

	}

	public boolean isWorkbookFileCreated() {

		return workbookFile != null;

	}

	public void closeSheetWriters() {

		for (SheetWriter sheetWriter : sheetWriters) {

			sheetWriter.closeSheet();

		}

	}

	/**
	 * 
	 * @return
	 * 
	 * @see java.lang.Iterable#iterator()
	 */

	@Override
	public Iterator<WorksheetModel> iterator() {

		return worksheets.iterator();

	}

	public String getExtention() {

		return extention;

	}

	public void setExtention(String extention) {

		this.extention = extention;

	}

}
