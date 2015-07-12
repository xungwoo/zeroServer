package com.thirtygames.zero.common.etc.document.writer;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.NotImplementedException;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class CellWriter implements Cell {
	/**
	 * 
	 */
	private RowWriter rowWriter;

	private final int column;

	private CellStyle cellStyle;

	public CellWriter(RowWriter rowWriter, int column) {
		this.rowWriter = rowWriter;
		this.column = column;
	}

	@Override
	public int getColumnIndex() {
		throw new NotImplementedException();
	}

	@Override
	public int getRowIndex() {
		throw new NotImplementedException();
	}

	@Override
	public Sheet getSheet() {
		throw new NotImplementedException();
	}

	@Override
	public Row getRow() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCellType(int cellType) {
		throw new NotImplementedException();
	}

	@Override
	public int getCellType() {
		throw new NotImplementedException();
	}

	@Override
	public int getCachedFormulaResultType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCellValue(RichTextString value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCellValue(String value) {
		rowWriter.createCell(column, value, getCellStyleIndex());
	}

	@Override
	public void setCellValue(double value) {
		rowWriter.createCell(column, value, getCellStyleIndex());
	}

	@Override
	public void setCellValue(Date value) {
		rowWriter.createCell(column, DateUtil.getExcelDate(value), getCellStyleIndex());
	}

	@Override
	public void setCellValue(Calendar value) {
		rowWriter.createCell(column, value, getCellStyleIndex());
	}

	@Override
	public void setCellValue(boolean value) {
		rowWriter.createCell(column, Boolean.toString(value));
	}

	@Override
	public void setCellFormula(String formula) throws FormulaParseException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCellFormula() {
		throw new UnsupportedOperationException();
	}

	@Override
	public RichTextString getRichStringCellValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getStringCellValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getNumericCellValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDateCellValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBooleanCellValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCellErrorValue(byte value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getErrorCellValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	@Override
	public CellStyle getCellStyle() {
		return cellStyle;
	}

	private int getCellStyleIndex() {
		return cellStyle != null ? cellStyle.getIndex() : -1;
	}

	@Override
	public void setAsActiveCell() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCellComment(Comment comment) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Comment getCellComment() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeCellComment() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Hyperlink getHyperlink() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHyperlink(Hyperlink link) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CellRangeAddress getArrayFormulaRange() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPartOfArrayFormulaGroup() {
		throw new UnsupportedOperationException();
	}

}
