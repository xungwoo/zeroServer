package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminStageStats extends GridFilter {

	String stageKey;
	String chapter;
	String stage;
	Integer clearMode; 
	Float playTimeAvg;
	Float gainGoldAvg;
	Float winAvg;
	Integer playCount;
}
