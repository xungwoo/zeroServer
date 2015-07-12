package com.thirtygames.zero.admin.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;

@Component
public class ResourceValidator implements BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountId", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cash", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gold", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fp", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unlockKey", "field.required");
	}

	@Override
	public void processErrors(Errors errors)  {
		ValidationUtil.processErrors(errors);
	}
}
