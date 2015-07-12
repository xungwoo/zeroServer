package com.thirtygames.zero.common.etc.document.writer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.opc.StreamHelper;
import org.apache.poi.util.TempFile;

public class WorkbookFile {

	/**
	 * name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
	 */

	private String sheetRef;

	private File workbook;

	private File sheet;

	public WorkbookFile(String sheetRef, String fileDescriptorId) {

		this.sheetRef = sheetRef.substring(1);

		this.workbook = TempFile.createTempFile("template" + fileDescriptorId,
				".xlsx");

		this.sheet = TempFile
				.createTempFile("sheet" + fileDescriptorId, ".xml");

	}

	public void substitute(File file) throws IOException {

		substitute(FileUtils.openOutputStream(file));

	}

	public void substitute(OutputStream out) throws IOException {

		ZipOutputStream zippedOut = new ZipOutputStream(out);

		ZipFile zippedWorkbook = new ZipFile(workbook);

		@SuppressWarnings("unchecked")
		Enumeration<ZipEntry> zipEntries = (Enumeration<ZipEntry>) zippedWorkbook
				.entries();

		while (zipEntries.hasMoreElements()) {

			ZipEntry zipEntry = zipEntries.nextElement();

			if (!StringUtils.equals(zipEntry.getName(), sheetRef)) {

				zippedOut.putNextEntry(new ZipEntry(zipEntry.getName()));

				InputStream is = zippedWorkbook.getInputStream(zipEntry);

				StreamHelper.copyStream(is, zippedOut);

				is.close();

			}

		}

		zippedOut.putNextEntry(new ZipEntry(sheetRef));

		InputStream is = FileUtils.openInputStream(sheet);

		StreamHelper.copyStream(is, zippedOut);

		is.close();

		zippedOut.close();

	}

	public File getWorkbook() {

		return workbook;

	}

	public File getSheet() {

		return sheet;

	}

	public File getWorkbookArchive() {

		File file = TempFile.createTempFile("workbook", ".xlsx");

		try {

			substitute(file);

		} catch (IOException ioe) {

		}

		return file;

	}

	public void closeWorkbookArchive() {

		try {
			FileUtils.forceDelete(sheet);
			FileUtils.forceDelete(workbook);
		} catch (IOException e) {
		}

	}

}
