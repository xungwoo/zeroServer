package com.thirtygames.zero.api.validator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.model.Ladder;

@Slf4j
@Component
public class LadderValidator implements BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return (Ladder.class.isAssignableFrom(clazz));
	}

	@Override
	public void validate(Object target, Errors errors) {
		log.debug("validator::" + target);
		Ladder ladder = (Ladder) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ladder", "field.required");
		if (ladder.getWin().equals(ladder.getLose())) {
			errors.rejectValue("win", "Win and lose cannot equal.");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "win", "field.required");
		if (ladder.getWin() != null && (ladder.getWin() != 0 && ladder.getWin() != 1)) {
			errors.rejectValue("win", "Win must 0 or 1.");
		}
		
		if (ladder.getLose() != null && (ladder.getLose() != 0 && ladder.getLose() != 1)) {
			errors.rejectValue("lose", "Lose must 0 or 1.");
		}
		
		if (ladder.getWinningStreakCnt() != null && (ladder.getWinningStreakCnt() != 0 && ladder.getWinningStreakCnt() != 1)) {
			errors.rejectValue("winningStreakCnt", "WinningStreak must 0 or 1.");
		}
		
	}

	@Override
	public void processErrors(Errors errors)  {
		ValidationUtil.processErrors(errors);
	}

}