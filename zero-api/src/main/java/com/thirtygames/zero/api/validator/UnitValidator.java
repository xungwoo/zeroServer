package com.thirtygames.zero.api.validator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.Deck;
import com.thirtygames.zero.common.model.Unit;

@Slf4j
@Component
public class UnitValidator implements BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (Deck.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
	}
	
	public void validateLevelUp(Unit unit, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unitId", "unitId.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "level", "level.required");
		int level = unit.getLevel();
		if (level > 0 && (level % 10) == 0) {
			errors.reject("LimitExceed.Level.");
		}
	}

	public void validateLimitExceed(Unit unit, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unitId", "unitId.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "level", "level.required");		
		int level = unit.getLevel();
		if (level <= 0 || (level % 10) != 0) {
			errors.reject("Not.available.limitExceed.level");
		}
	}

	public void validateResource(AccountResource ar, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountId", "accountId.required");
		
		Long gold = ar.getGold();
		if (gold != null && gold < 0) {
			errors.reject("gold.only.positive.number");
		}
		
		Long cash = ar.getCash();
		if (cash != null && cash < 0) {
			errors.reject("cash.only.positive.number");			
		}
	}

	@Override
	public void processErrors(Errors errors)  {
		ValidationUtil.processErrors(errors);
	}
}
