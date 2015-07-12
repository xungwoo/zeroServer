package com.thirtygames.zero.admin.form.converter;

import org.springframework.util.StringUtils;

public class FloatConverter implements TypeConverter<String, Float> {

	@Override
	public Float convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		return Float.valueOf(source);
	}

}
