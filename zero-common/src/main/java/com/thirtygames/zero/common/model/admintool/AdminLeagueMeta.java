package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminLeagueMeta extends GridFilter {

	String metaKey;
	Integer league;
	Integer costBp;	
	Float goldBonus4Regame;
	Integer rewardType;	// gold, cash
	Integer rewardWinner;
	Integer rewardLooser;
	Integer leagueUp;
	Integer leagueDown;
	
	String modId;
	String modYmdt;
}
