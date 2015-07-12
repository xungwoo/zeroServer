package com.thirtygames.zero.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PeriodItem extends GridFilter {

	String accountId;
	Integer metaItemKey;
	Integer repeatType;
	Long startYmdt;
	Long endYmdt;
	String buyYmdt;
	
	//param
	String startYmd;
	String endYmd;
		
}
