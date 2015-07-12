package com.thirtygames.zero.common.model.admintool;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DeckGrid extends GridFilter implements Serializable  {
	private static final long serialVersionUID = 2850797522028512410L;
	
	public static final int TEAM_MAX = 1;
	public static final int POSITION_MAX = 3;
	
	String deckKey;
	String accountId;
	Integer teamId;
	Integer positionId;
	String unitId;
	String deckUnitTypeLevel;
	
	Integer unitType;
	Integer level;
	Date limitExceedEndYmdt;
	Integer skill0Lv;
	Integer skill1Lv;
	Integer skill2Lv;
	Integer skill3Lv;
	Date genYmdt;

	
	
	// params
	String unitIds;
}
