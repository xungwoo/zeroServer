package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminEventDropRate extends GridFilter {
	
	public final static int DAY_LIGHT_SAVING_SEC = 3600;
	
	String eventKey;
	Float itemDropRateBoost;
	Float goldDropBoost;
	
	String startDate;
	String endDate;
	
	String startTime;
	String endTime;
	Long localStartTime;
	Long localEndTime;
	
	Boolean sunday;
	Boolean monday;
	Boolean tuesday;
	Boolean wednesday;
	Boolean thursday;
	Boolean friday;
	Boolean saturday;
	
	Integer dayWeek;
	
	String modYmdt;
	String modId;	
}
