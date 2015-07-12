package com.thirtygames.zero.api.validator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.model.log.CheatLog;

@Slf4j
@Component
public class CheatLogValidator implements BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (CheatLog.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
	}
	

	@Override
	public void processErrors(Errors errors)  {
		ValidationUtil.processErrors(errors);
	}
}
