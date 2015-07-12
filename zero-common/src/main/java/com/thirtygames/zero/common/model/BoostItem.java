package com.thirtygames.zero.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BoostItem extends GridFilter {
	
	public static final String ITEM_DROP_BOOST = "ItemDropBoost";
	public static final String GOLD_DROP_BOOST = "GoldDropBoost";

	String accountId;
	Integer boostType;
	Float itemDropBoost;
	Float goldDropBoost;
	
	Long startYmdt;
	Long endYmdt;
	Integer durationTime;
	Integer duplicationCount;
	String buyYmdt;
		
	
	@RequiredArgsConstructor
	public enum BoostType {
		Gold(1), 
		Item(2); 
		
		@Getter
		private final int code;
	}
}
