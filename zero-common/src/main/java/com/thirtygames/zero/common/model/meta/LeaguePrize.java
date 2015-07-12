package com.thirtygames.zero.common.model.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class LeaguePrize implements Serializable {
	private static final long serialVersionUID = -894094511390553019L;

	Integer prizeId;
	Integer league;
	
	Integer prizeType;
	Integer prizeValueMin;
	Integer prizeValueMax;
	Integer rewardType;
	Long reward;
	
}



