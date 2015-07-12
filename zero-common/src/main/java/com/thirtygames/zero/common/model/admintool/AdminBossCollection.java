package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminBossCollection  extends GridFilter {

	
	String metaKey; 
	Integer collectionId;
	
	String bossMetaIds;
	String title;
	Integer difficulty;
	
	Integer rewardType; 
	Long reward; 
	
	String modYmdt;
	String modId;
	
}
