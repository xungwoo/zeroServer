package com.thirtygames.zero.common.model.params;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.generic.GenericModel;

@Slf4j
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MatchingAI extends GenericModel implements Serializable  {
	
	// unit level max 30  
	// skill Level max 36
	// 66 x 3 = 196
	// eqPoint max 75 / 2 = 38
	// max 234
	
	public final static int MAX_TOTAL_LEVEL_SUM = 234;
	public final static int SEARCH_RANGE = 30;
	public final static float SEARCH_RANGE_SCALE_UP = 1.5f;
	
	String accountId;
	Integer minTotalLevelSum = 0;
	Integer maxTotalLevelSum = MAX_TOTAL_LEVEL_SUM;
	Integer league;
	Integer searchRange = SEARCH_RANGE;
	
	
	public void calcSearchRange(int totalLevelSum, int eqPoint, int step, int ladder) {
		if (totalLevelSum <= 0 || step < 0) {
			throw new RestException("Invalid.Param");
		}
		
		this.searchRange = SEARCH_RANGE * step;
		int minusRange = (int) (this.searchRange * makeMinusWeight(ladder));
		log.debug("minusRange::" + minusRange);
		this.minTotalLevelSum = totalLevelSum + (eqPoint / 2) + minusRange;
		log.debug("minTotalLevelSum::" + minTotalLevelSum);
		this.maxTotalLevelSum = totalLevelSum + (eqPoint / 2) + searchRange + minusRange;
		log.debug("maxTotalLevelSum::" + maxTotalLevelSum);
	}
	
	
	public float makeMinusWeight(int ladder) {
		float minusWeight;
		if(ladder <= 1000) {
			minusWeight = -0.8f;
		} else if (ladder <= 1100) {
			minusWeight = -0.7f;
		} else if (ladder <= 1200) {
			minusWeight = -0.6f;
		} else if (ladder <= 1500) {
			minusWeight = -0.5f;
		} else if (ladder <= 2000) {
			minusWeight = -0.4f;
		} else {
			minusWeight = -0.3f;
		}
		return minusWeight;
	}

	
}
