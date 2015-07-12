package com.thirtygames.zero.common.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BossRaidParty implements Serializable {

	String friendId;
	Long memberNo;
	String nickName;
	Integer title;
	
	Integer league;
	
	Float damageSum;
	Float damagePercent;
	
	Integer rankNum;
	
	Integer rewardType1;
	Long reward1;
	Integer rewardType2;
	Long reward2;
	Integer rewardType3;
	Long reward3;
	
}
