package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminUnitReset extends GridFilter {
	String unitId;
	String accountId;
	Integer unitType;
	Integer level;
	Integer limitExceedEndYmdt;
	Integer skill0Lv;
	Integer skill1Lv;
	Integer skill2Lv;
	Integer skill3Lv;
	
	Long resetGold;
	Long resetUnlockKey;
}
