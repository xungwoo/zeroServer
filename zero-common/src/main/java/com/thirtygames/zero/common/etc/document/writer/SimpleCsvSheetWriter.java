package com.thirtygames.zero.common.etc.document.writer;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import au.com.bytecode.opencsv.CSVWriter;

import com.thirtygames.zero.common.etc.document.CellType;
import com.thirtygames.zero.common.etc.document.ColumnDescriptor;

public class SimpleCsvSheetWriter implements SheetWriter {
	private static final String FORMULA_FORMAT = "=\"%s\"";

	private CSVWriter csvWriter;

	private List<ColumnDescriptor> descriptors;

	private Writer writer;

	public SimpleCsvSheetWriter(List<ColumnDescriptor> descriptors) {
		this(descriptors, new StringWriter());
	}

	public SimpleCsvSheetWriter(List<ColumnDescriptor> descriptors, Writer writer) {
		this.descriptors = descriptors;
		this.writer = writer;

		initialize();
	}

	private void initialize() {
		csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER);

		String[] rowEntry = new String[descriptors.size()];
		for (int i = 0; i < descriptors.size(); i++) {
			rowEntry[i] = descriptors.get(i).getHeaderName();
		}

		csvWriter.writeNext(rowEntry);
	}

	public void writeRowEntries(List<?> rowEntries) {
		int rownum = 1;
		for (Object rowEntry : rowEntries) {
			writeRowEntry(rownum++, rowEntry);
		}
	}

	/**
	 * @param rownum
	 * @param entry
	 * @see com.nbp.nmp.order.service.component.document.writer.SheetWriter#writeRowEntry(int, java.lang.Object)
	 */
	@Override
	public void writeRowEntry(int rownum, Object entry) {
		String[] rowEntry = new String[descriptors.size()];
		int i = 0;
		for (ColumnDescriptor descriptor : descriptors) {
			Object objValue = descriptor.getValue(entry);
			String strValue = "";
			if (objValue != null) {
				CellType cellType = descriptor.getCellType();
				switch (cellType) {
					case DATE:
					case TIME:
						strValue = DateFormatUtils.ISO_DATE_FORMAT.format((Date)objValue);
						break;
					case TEXT:
						strValue = objValue instanceof String ? (String)objValue : String.valueOf(objValue);
						
						// 줄바꿈 escaping
						strValue = strValue.replaceAll("\n", " ").replaceAll("\r", "");
						break;
					case NUMBER:
					case CURRENCY:
					default:
						strValue = ObjectUtils.toString(objValue);
						break;
				}
				strValue = StringUtils.replaceEach(strValue, new String[]{"\"", ",", "\n"}, new String[]{"'", ";", " "});
			}
			rowEntry[i++] = String.format(FORMULA_FORMAT, strValue);
		}
		csvWriter.writeNext(rowEntry);
	}

	public String getCsvContent() {
		IOUtils.closeQuietly(writer);

		return writer.toString();
	}

	@Override
	public void closeSheet() {
		
	}
}
