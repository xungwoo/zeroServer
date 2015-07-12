package com.thirtygames.zero.common.model.admintool;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminStageClear extends GridFilter implements Serializable {

	String clearKey;
	String accountId;
	String stageKey;
	Integer chapter;
	Integer stage;

	Integer clearMode;
	Integer clearStep;
	Float clearProgress;
	Integer exposedScenes;
	String modYmdt;

}
