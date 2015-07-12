package com.thirtygames.zero.common.model.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class LeagueMeta implements Serializable {
	private static final long serialVersionUID = 6826245075104056852L;
	
	Integer league;
	Integer costBp;	
	Float goldBonus4Regame;
	Integer rewardType;	// gold, cash
	Integer rewardWinner;
	Integer rewardLooser;
	Integer leagueUp;
	Integer leagueDown;
	
}
