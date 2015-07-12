package com.thirtygames.zero.common.etc.spring.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.StreamHelper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.thirtygames.zero.common.etc.document.CellHandler;
import com.thirtygames.zero.common.etc.document.CellHandlerHelper;
import com.thirtygames.zero.common.etc.document.CellStyleHelper;
import com.thirtygames.zero.common.etc.document.CellType;
import com.thirtygames.zero.common.etc.document.model.WorkbookModel;

@Slf4j
public abstract class AbstractSpreadSheetView extends AbstractView {
	protected static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	protected static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";

	protected Map<CellType, CellHandler> cellHandlers;

	protected String extention = ".xlsx";

	private String url;

	/**
	 * Renders the Excel view, given the specified model.
	 */
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WorkbookModel workbookModel = (WorkbookModel)model.get("workbookModel");
		if (workbookModel == null) {
			throw new IllegalArgumentException("Model에서 'workbookModel' 속성을 찾을 수가 없네여~ (대소문자 주의!!) >.<");
		}
		
		extention = "." + workbookModel.getExtention();

		workbookModel.closeSheetWriters();
		if (!workbookModel.isWorkbookFileCreated()) {
			Workbook workbook = workbookModel.getWorkbook();
			if (workbook == null) {
				if (StringUtils.isNotBlank(url)) {
					workbook = getTemplateSource(url, request);
				} else {
					workbook = StringUtils.equals(extention, ".xlsx") ? new XSSFWorkbook() : new HSSFWorkbook();
				}
			}

			buildSpreadSheetDocument(model, workbook, createCellStyles(workbook), request, response);

			if (workbookModel.isUsingAutoSizeColumn()) {
				for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
					autoSizeColumn(workbook.getSheetAt(i));
				}
			}
			finishResponse(request, response, workbook, workbookModel.getWorkbookName());
		} else {
			finishResponse(request, response, workbookModel.getWorkbookFile().getWorkbookArchive(), workbookModel.getWorkbookName());
		}
	}

	private void finishResponse(HttpServletRequest request, HttpServletResponse response, File workbookFile, String workbookName) throws IOException {
		setResponseHeader(request, response, workbookName);
		ServletOutputStream out = response.getOutputStream();
		InputStream is = new FileInputStream(workbookFile);
		StreamHelper.copyStream(is, out);
		is.close();
		out.flush();
	}

	private void finishResponse(HttpServletRequest request, HttpServletResponse response, Workbook workbook, String workbookName) throws IOException {
		setResponseHeader(request, response, workbookName);
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		out.flush();
	}

	private void setResponseHeader(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		response.setContentType(getContentType());
		//		response.setHeader("Content-Transfer-Encoding", "binary");
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.contains(userAgent, "MSIE") || StringUtils.contains(userAgent, "rv:")) {
			response.setHeader("Content-Disposition", String.format("attachment; filename=%s;", URLEncoder.encode(fileName + extention, "UTF-8")));
		} else {
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\";", new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + extention));
		}

	}

	protected Workbook getTemplateSource(String url, HttpServletRequest request) throws Exception {
		LocalizedResourceHelper helper = new LocalizedResourceHelper(getApplicationContext());
		Resource inputFile = helper.findLocalizedResource(url, extention, RequestContextUtils.getLocale(request));

		// Create the Excel document from the source.
		if (logger.isDebugEnabled()) {
			logger.debug("Loading Excel workbook from " + inputFile);
		}

		InputStream is = inputFile.getInputStream();
		return StringUtils.equals(extention, ".xlsx") ? new XSSFWorkbook(is) : new HSSFWorkbook(is);
	}

	protected abstract void buildSpreadSheetDocument(Map<String, Object> model, Workbook workbook, Map<CellType, CellStyle> cellStyles, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * {@link Warning http://poi.apache.org/spreadsheet/quick-guide.html#Autofit}
	 * 
	 * @param sheet
	 */
	protected void autoSizeColumn(Sheet sheet) {
		int column = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum();
		for (int i = 0; i < column; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	protected Cell getCell(Row row, int column) {
		Cell cell = row.getCell(column);
		if (cell == null) {
			cell = row.createCell(column);
		}
		return cell;
	}

	protected void setCellValue(Row row, int column, CellStyle cellStyle, CellType cellType, Object value) {
		CellHandler cellHandler = cellHandlers.get(cellType);
		setCellValue(row, column, cellStyle, cellHandler, value);
	}

	protected void setCellValue(Row row, int column, CellStyle cellStyle, CellHandler cellHandler, Object value) {
		Cell cell = getCell(row, column);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
		if (value != null && cellHandler != null) {
			cellHandler.setCellValue(cell, value);
		}
	}

	protected Map<CellType, CellStyle> createCellStyles(Workbook workbook) {
		return new CellStyleHelper().getCellStyles(workbook);
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@PostConstruct
	public void initialize() {
		setContentType(StringUtils.equals(extention, ".xlsx") ? XLSX_CONTENT_TYPE : XLS_CONTENT_TYPE);
		cellHandlers = new CellHandlerHelper().getCellHandlers();
	}

}
