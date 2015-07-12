package com.thirtygames.zero.common.etc.util;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

public class AjaxResponseUtil {
	public static final String RESULT = "json/json_result";
	
	public static final String RESULT_VALIDERROR = "json/json_validation_error";
	
	public static final String RESULT_ERROR = "json/json_error";

	public static String jsonResult(Object obj, ModelMap model) {
		model.addAttribute("_result", JacksonUtil.toJson(obj));
		return RESULT;
	}
	
	public static String jsonValidationError(BindingResult result, ModelMap model) {
		model.addAttribute("_result", result);

		return RESULT_VALIDERROR;
	}
	
	public static String jsonResultError(String errorCode, String errorMessage, Object result, ModelMap model) {
		model.addAttribute("_errorCode", errorCode);
		model.addAttribute("_errorMessage", errorMessage);
		model.addAttribute("_result", JacksonUtil.toJson(result));
		return RESULT_ERROR;
	}
}
