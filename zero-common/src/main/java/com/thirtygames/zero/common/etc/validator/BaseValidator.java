package com.thirtygames.zero.common.etc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public interface BaseValidator extends Validator {

	public void processErrors(Errors errors) ;
	
}
