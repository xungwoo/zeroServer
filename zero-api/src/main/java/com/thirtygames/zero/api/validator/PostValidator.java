package com.thirtygames.zero.api.validator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.model.Post;

@Slf4j
@Component
public class PostValidator implements BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (Post.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
	
	}

	@Override
	public void processErrors(Errors errors)  {
		ValidationUtil.processErrors(errors);
	}

}