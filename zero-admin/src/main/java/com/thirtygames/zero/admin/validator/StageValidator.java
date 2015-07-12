package com.thirtygames.zero.admin.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.etc.validator.BaseValidator;

@Component
public class StageValidator implements BaseValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stageKey", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "chapter", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stage", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clearMode", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "playMode", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "costAp", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "costBp", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "limitTime", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "goldScaleForEnemyKill", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scene", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unlockKeyDropRate", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemDropFairAdder", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemDropRate", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemGrade", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemGradeRange", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bossItemDropRate", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bossItem", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bossItemCategory", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bossItemGrade", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bossCameraTime", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hiddenOpenRate", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "chapterBossStage", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "enemyLvAdder", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "enemySkillAdder", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "enemyHpScaler", "field.required");
	}

	@Override
	public void processErrors(Errors errors)  {
		ValidationUtil.processErrors(errors);
	}
}
