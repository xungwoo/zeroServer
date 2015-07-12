package com.thirtygames.zero.admin.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.model.log.ResourceLog;

@Component
public class ResourceLogValidator implements BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (ResourceLog.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processErrors(Errors errors)  {
		ValidationUtil.processErrors(errors);
	}
}
