package com.thirtygames.zero.common.etc.document.model;

import java.util.List;

import com.thirtygames.zero.common.etc.document.DefaultColumnDescriptor;

public class WorksheetModel {

	/**

	 * 

	 */

	private List<DefaultColumnDescriptor> descriptors;

	private List<?> rowEntries;

	private String worksheetName;

	public WorksheetModel(List<DefaultColumnDescriptor> descriptors,
			List<?> rowEntries, String worksheetName) {

		this.descriptors = descriptors;

		this.rowEntries = rowEntries;

		this.worksheetName = worksheetName;

	}

	public List<DefaultColumnDescriptor> getDescriptors() {

		return descriptors;

	}

	public void setDescriptors(List<DefaultColumnDescriptor> descriptors) {

		this.descriptors = descriptors;

	}

	public List<?> getRowEntries() {

		return rowEntries;

	}

	public void setRowEntries(List<?> rowEntries) {

		this.rowEntries = rowEntries;

	}

	public String getWorksheetName() {

		return worksheetName;

	}

	public void setWorksheetName(String worksheetName) {

		this.worksheetName = worksheetName;

	}

}