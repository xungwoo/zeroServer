package com.thirtygames.zero.admin.validator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.model.equipment.Equipment;

@Slf4j
@Component
public class EquipmentValidator implements BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (Equipment.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
	}
	

	@Override
	public void processErrors(Errors errors)  {
		ValidationUtil.processErrors(errors);
	}	

}
