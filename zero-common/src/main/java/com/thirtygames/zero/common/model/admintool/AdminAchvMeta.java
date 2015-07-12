package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminAchvMeta extends GridFilter {

	String achvMetaKey;
	String achievementKey;
	Integer achievementId; // 메인 키
	Integer step;
	Boolean isLastStep;
	Integer missionType;
	Integer param1;
	Integer param2;
	Integer param3;
	Integer goal; // 목표 량
	Integer rewardType; // 보상 타입
	Long reward; // 보상액

	String titleKo;
	String descriptionKo;
	String titleEn;
	String descriptionEn;
	
	String modId;
	String modYmdt;
	
	@JsonIgnore String lang;
}
