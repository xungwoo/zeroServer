package com.thirtygames.zero.common.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Castle implements Serializable {
	
	Integer castleKey;
	Integer castleId;
	String scene; 
	String accountId;
	
	Integer floor;
    Integer unitType;
    Integer startingLevel;
    Integer maxLevel;
    Float hpScaler;
    Float goldPerUnit;   // 유닛당 골드
    Float goldAdderPerLevel;   
    Float equipDropRate;  // 장비 드랍율 0이면 안줌
    
    //Integer ap;
    Integer fee;
    
    Integer unitCount;
    Float unitScale;
    Integer unitLimit;
    Float spawnSpeed;
    
    String title;
    Integer visible;  // 노출여부
    String recommendationUnit;
    String allowUnit;
    
    
    Integer lastClearLevel;	// user 
    Integer castlePoint;
    Integer castleLastClearFloor;
    
    // params
    Boolean isClear;
    Integer castleLevel;
    Integer castleFloor;
    Long rewardGold;
}
