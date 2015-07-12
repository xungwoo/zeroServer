package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminCastleMeta extends GridFilter {
	
	String metaKey;
	Integer castleId;
	String scene; 
	
	Integer floor;
    Integer unitType;
    Integer startingLevel;
    Integer maxLevel;
    Float hpScaler;
    Float goldPerUnit;   // 유닛당 골드
    Float goldAdderPerLevel;   
    Float equipDropRate;  // 장비 드랍율 0이면 안줌
    
    Integer fee;
    
    Integer unitCount;
    Float unitScale;
    Integer unitLimit;
    Float spawnSpeed;
    
    String title;
    Integer visible;  // 노출여부
    
    String recommendationUnit;
    String allowUnit;

	String modYmdt;
	String modId;

}
