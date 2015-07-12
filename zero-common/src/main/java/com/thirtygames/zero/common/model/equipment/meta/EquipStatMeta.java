package com.thirtygames.zero.common.model.equipment.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EquipStatMeta extends GridFilter {
	private static final long serialVersionUID = 5867667720905133320L;

	Integer statMetaKey;
	Integer equipMetaKey;
	Integer statType;
	Integer grade;
	Integer decoCode;
	Integer baseStat; // 1 : base, 0 : deco
	Integer setStat; // 0 not, 1 full
	Integer onlyFor; // unitType
	Integer gemStatType;

	Float minRange1;
	Float minRange2;
	Float maxRange1;
	Float maxRange2;
	
	Float statRateMin;
	Float statRateMax;
	Float durationMin;
	Float durationMax;
	
	Float rate;
	
	// Deco Stat
	String statName;
	String statEngName;
	Integer weight;
	
}
