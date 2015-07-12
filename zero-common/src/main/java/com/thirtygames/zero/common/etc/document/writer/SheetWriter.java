package com.thirtygames.zero.common.etc.document.writer;

public interface SheetWriter {

	/**
	 * 
	 * @param rownum
	 * 
	 * @param entry
	 */

	void writeRowEntry(final int rownum, Object entry);

	void closeSheet();

}
