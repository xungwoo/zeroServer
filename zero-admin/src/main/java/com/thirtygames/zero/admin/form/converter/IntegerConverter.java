package com.thirtygames.zero.admin.form.converter;

import org.springframework.util.StringUtils;

public class IntegerConverter implements TypeConverter<String, Integer> {

	@Override
	public Integer convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		return Integer.valueOf(source);
	}

}
