package com.thirtygames.zero.admin.form.converter;

import org.springframework.util.StringUtils;

public class BooleanConverter implements TypeConverter<String, Boolean> {

	@Override
	public Boolean convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		return Boolean.valueOf(source);
	}

}
