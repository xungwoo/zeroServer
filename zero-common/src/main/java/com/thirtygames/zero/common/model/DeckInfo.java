package com.thirtygames.zero.common.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class DeckInfo extends GridFilter implements Serializable  {
	
	String infoKey;
	String accountId;
	Integer unitLevelSum;
	Integer skillLevelSum;
	Integer totalLevelSum;
	Integer equipmentPoint;
	String deckUnitTypeLevelStr;
	
	
	Integer title;
	String nickName;
	
}
