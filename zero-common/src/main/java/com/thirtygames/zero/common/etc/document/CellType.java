package com.thirtygames.zero.common.etc.document;

import java.util.Date;

public enum CellType {

	HEADER(String.class),

	TEXT(String.class),

	DATE(Date.class),

	TIME(Date.class),

	NUMBER(Number.class),

	CURRENCY(NUMBER.getConvertibleClasses()),

	RATIO(NUMBER.getConvertibleClasses()),

	BOOLEAN(Boolean.class);

	private Class<?>[] classes;

	CellType(Class<?>... classes) {

		this.classes = classes;

	}

	public Class<?>[] getConvertibleClasses() {

		return classes;

	}

	/*
	 * @Override
	 * 
	 * public String toString() {
	 * 
	 * return ToStringBuilder.reflectionToString(this);
	 * 
	 * }
	 */

}
