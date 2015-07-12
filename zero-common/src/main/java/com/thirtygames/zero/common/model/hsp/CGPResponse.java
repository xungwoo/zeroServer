package com.thirtygames.zero.common.model.hsp;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CGPResponse {

	Long transactionId;
	Integer resultCode;
	
	
	List<CGPReward> rewardList;

}
