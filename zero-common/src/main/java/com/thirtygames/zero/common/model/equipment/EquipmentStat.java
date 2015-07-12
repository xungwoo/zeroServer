package com.thirtygames.zero.common.model.equipment;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EquipmentStat implements Serializable {
	private static final long serialVersionUID = 5867667720905133320L;

	@JsonIgnore Integer statKey;
	@JsonIgnore String equipmentId;
	@JsonIgnore String accountId;

	Integer type;
	Integer decoCode;
	Float min;
	Float max;
	Float statRate;
	Float duration;

	@JsonIgnore Float minRandom;
	@JsonIgnore Float maxRandom;
	@JsonIgnore Float rateRandom;
	@JsonIgnore Float durRandom;
	
	String statEngName;
	
	Integer gemStatType;
	Integer baseStat;	// 1:base, 0:deco
	Integer setStat;	// 0:all, 1:full, 2:half
	Integer onlyFor; // unitType

}
