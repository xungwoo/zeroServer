package com.thirtygames.zero.admin.form.converter;

@SuppressWarnings("hiding")
public interface TypeConverter<String, T> {
	T convert(String source);
}
