package com.thirtygames.zero.common.model.equipment.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EquipStatUpdate extends GridFilter {
	private static final long serialVersionUID = 5867667720905133320L;

	String accountId;
	String equipmentId;
	Integer equipmentType;
	
	Integer statKey;
	Integer type;
	Integer baseStat; // 1 : base, 0 : deco
	Integer setStat; // 0 // all 1, full 2 half
	Integer onlyFor; // unitType
	
	Integer grade;

	Float minRange1;
	Float minRange2;
	Float maxRange1;
	Float maxRange2;
	
	Float statRateMin;
	Float statRateMax;
	Float durationMin;
	Float durationMax;
	
	Float min;
	Float max;
	Float statRate;
	Float duration;
	
	@JsonIgnore Float minRandom;
	@JsonIgnore Float maxRandom;
	@JsonIgnore Float rateRandom;
	@JsonIgnore Float durRandom;
	
	
	
}
