package com.thirtygames.zero.admin.form.converter;

import org.springframework.util.StringUtils;

public class LongConverter implements TypeConverter<String, Long> {

	@Override
	public Long convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		return Long.valueOf(source);
	}

}
