package com.thirtygames.zero.common.etc.document.result;

public interface RowConverter<E, T> {
	E convert(T element);
}
