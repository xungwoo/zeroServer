package com.thirtygames.zero.api.validator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.google.common.base.CharMatcher;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.model.Deck;

@Slf4j
@Component
public class DeckValidator implements BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (Deck.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
		Deck deck = (Deck) target;

//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountId", "field.required");
//		if (deck.getAccountId().length() != 36) {
//			errors.rejectValue("accountId", "field.format", new Object[] { 0 }, null);
//		}
		
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teamId", "teamId.required");
//		if (deck.getTeamId() <= 0 || deck.getTeamId() > Deck.TEAM_MAX) {
//			errors.rejectValue("teamId", "teamId.range.error", null, null);
//		}
	}
	
	public void validateUpdateTeam(Object target, Errors errors) {
		Deck deck = (Deck) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unitIds", "unitIds.required");
		
		int matchCount = CharMatcher.is(':').countIn(deck.getUnitIds());
		if (matchCount != Deck.POSITION_MAX - 1) {
			errors.rejectValue("unitIds", "unitIds.size.error", null, null);
		}
		
//		Iterable<String> unitIds = Splitter.on(":").trimResults().split(deck.getUnitIds());
//		for(String unitId : unitIds) {
//			if(StringUtils.isEmptyOrWhitespaceOnly(unitId)) {
//				errors.rejectValue("unitIds", "unitId.no.empty");
//			}
//		}
	}

	@Override
	public void processErrors(Errors errors)  {
		ValidationUtil.processErrors(errors);
	}

}
