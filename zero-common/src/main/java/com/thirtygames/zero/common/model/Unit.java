package com.thirtygames.zero.common.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Unit extends GridFilter {
	private static final long serialVersionUID = 1053070068242758058L;

	public static final int DEFAULT_LEVEL = 1;

	String unitKey;
	String unitId;
	String accountId;
	Integer unitType;
	Integer level;
	Integer limitExceedEndYmdt;
	Integer skill0Lv;
	Integer skill1Lv;
	Integer skill2Lv;
	Integer skill3Lv;

	List<Equipment> equipments;

	
	@JsonIgnore
	Integer remainderTime;
	@JsonIgnore
	Integer limitExceedTime;
	@JsonIgnore
	String deckUnitTypeLevel;
}
