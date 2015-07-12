package com.thirtygames.zero.common.model.hsp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CGPReward {

	String rewardCode;
	Integer rewardType;
	Long rewardValue;



//	"campaignId" : 7,
//    "promoDateBegin" : "2014-10-10 00:00:00",
//    "promoDateEnd" : "2014-10-11 00:00:00",
//    "rewardDateBegin" : "2014-10-10 00:00:00",
//    "rewardDateEnd" : "2014-10-12 00:00:00",
//    "rewardCode" : "gem",
//    "rewardValue" : 100

}
