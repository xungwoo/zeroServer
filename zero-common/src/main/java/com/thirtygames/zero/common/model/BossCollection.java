package com.thirtygames.zero.common.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BossCollection implements Serializable {

	
	Integer collectionId;
	String accountId;
	
	String bossMetaIds;
	List<BossRaid> bossRaidList;
	
	String title;
	Integer difficulty;
	
	Integer rewardType; 
	Long reward;	
	
	Boolean isComplete;
	Boolean isRewarded;
	
	// params
	String lang;
}
