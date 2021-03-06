package com.thirtygames.zero.api.validator.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.model.admindata.Notice;

@Slf4j
@Component
public class NoticeValidator implements BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (Notice.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
	
	}

	@Override
	public void processErrors(Errors errors)  {
		ValidationUtil.processErrors(errors);
	}

}