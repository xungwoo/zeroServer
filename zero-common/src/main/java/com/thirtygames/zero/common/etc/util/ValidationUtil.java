package com.thirtygames.zero.common.etc.util;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.thirtygames.zero.common.etc.exception.RestException;

@Slf4j
public class ValidationUtil {

	public static void processErrors(Errors errors)  {
		if (errors.hasErrors()) {
			List<ObjectError> errorList = errors.getAllErrors();
			String eStr = "Validate.Error.";
			if (errorList.size() > 0) {
				ObjectError o = errorList.get(0);
				eStr = o.getCode();
			}
			log.debug("Errors::" + errors.getAllErrors().toString());

			throw new RestException(eStr);
		}
	}
	
	
	
	public static void isNullModel(Object model) {
		if(model == null) {
			throw new RestException(com.thirtygames.zero.common.etc.error.Errors.NotFoundData);
		}
	}
	
	public static void isNullModel(Object model, String addMsg) {
		if(model == null) {
			log.debug((addMsg + " :: " + model));
			throw new RestException(com.thirtygames.zero.common.etc.error.Errors.NotFoundData, addMsg);
		}
	}
	
	public static void isNullOrEmptyList(List<?> list) {
		if(list.isEmpty()) {
			throw new RestException(com.thirtygames.zero.common.etc.error.Errors.NotFoundData);
		}
	}
	
	public static void isNullOrEmptyList(List<?> list, String addMsg) {
		if(list.isEmpty()) {
			log.debug((addMsg + " :: " + list));
			throw new RestException(com.thirtygames.zero.common.etc.error.Errors.NotFoundData, addMsg);
		}
	}
}
