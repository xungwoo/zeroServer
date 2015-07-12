package com.thirtygames.zero.common.etc.document;

public interface ColumnDescriptor {

	/**
	 * 
	 * @return
	 */

	String getHeaderName();

	/**
	 * 
	 * @return
	 */

	CellType getCellType();

	/**
	 * 
	 * @return
	 */

	CellHandler getCellHandler();

	/**
	 * 
	 * @param root
	 * 
	 * @return
	 */

	Object getValue(Object root);

	/**
	 * 
	 * @return
	 */

	int getWidth();

}
