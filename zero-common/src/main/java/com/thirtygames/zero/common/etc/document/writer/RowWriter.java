package com.thirtygames.zero.common.etc.document.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellReference;

public class RowWriter {
	/**
	 * 
	 */
	private final SilentWriter out;

	private final String encoding;

	private int rownum;

	public RowWriter(OutputStream os, String encoding) throws Exception {
		this.out = new SilentWriter(os, encoding);
		this.encoding = encoding;
	}

	public void beginSheet(List<Integer> widths) {
		out.write("<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>" + "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\"><cols>");
		for (Integer width : widths) {
			out.write(String.format("<col min=\"%d\" max=\"%d\" width=\"%d\" />", 1, widths.size(), width));
		}
		out.write("</cols><sheetData>\n");
	}

	public void endSheet() {
		out.write("</sheetData>");
		out.write("</worksheet>");
	}

	/**
	 * Insert a new row
	 * 
	 * @param rownum
	 *            0-based row number
	 */
	public void insertRow(int rownum) {
		out.write("<row r=\"" + (rownum + 1) + "\">\n");
		this.rownum = rownum;
	}

	/**
	 * Insert row end marker
	 */
	public void endRow() {
		out.write("</row>\n");
	}

	public void createCell(int columnIndex, String value, int styleIndex) {
		String ref = new CellReference(rownum, columnIndex).formatAsString();
		out.write("<c r=\"" + ref + "\" t=\"inlineStr\"");
		if (styleIndex != -1) {
			out.write(" s=\"" + styleIndex + "\"");
		}
		out.write(">");
		out.write("<is><t><![CDATA[" + value + "]]></t></is>");
		out.write("</c>");
	}

	public void createCell(int columnIndex, String value) {
		createCell(columnIndex, value, -1);
	}

	public void createCell(int columnIndex, double value, int styleIndex) {
		String ref = new CellReference(rownum, columnIndex).formatAsString();
		out.write("<c r=\"" + ref + "\" t=\"n\"");
		if (styleIndex != -1) {
			out.write(" s=\"" + styleIndex + "\"");
		}
		out.write(">");
		out.write("<v>" + value + "</v>");
		out.write("</c>");
	}

	public void createCell(int columnIndex, double value) {
		createCell(columnIndex, value, -1);
	}

	public void createCell(int columnIndex, Calendar value, int styleIndex) {
		createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
	}

	public void closeSilently() {
		try {
			out.close();
		} catch (IOException ioe) {
		}
	}

	private class SilentWriter {

		/**
		 * 
		 */
		private final Writer out;

		public SilentWriter(OutputStream os, String encoding) throws Exception {
			this.out = new OutputStreamWriter(os, encoding);
		}

		public void write(String str) {
			try {
				out.write(str);
			} catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
		}

		public void close() throws IOException {
			out.close();
		}
	}
}
