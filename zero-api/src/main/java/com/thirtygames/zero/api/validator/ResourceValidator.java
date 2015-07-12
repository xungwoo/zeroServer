package com.thirtygames.zero.api.validator;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.model.AccountResource;

@Component
public class ResourceValidator implements BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (AccountResource.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
	}
	
	public void validateRecharge(AccountResource ar, Errors errors) throws IllegalAccessException {
		validateMinusValue(ar, errors);
	}

	public void validateExhaust(AccountResource ar, Errors errors) throws IllegalAccessException {
		validateMinusValue(ar, errors);
	}
	
	private void validateMinusValue(AccountResource ar, Errors errors) throws IllegalAccessException {
		for (Field field : ar.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object propValue = field.get(ar);
			if (field.getType() == Long.class && propValue != null) {
				if (((Number) propValue).intValue() < 0) {
					errors.rejectValue(field.getName(), "Resource.field.only.plus.value", new Object[] { 0 }, null);
				}
			};
		}
	}

	@Override
	public void processErrors(Errors errors)  {
		// TODO Auto-generated method stub
		
	}
	

}
