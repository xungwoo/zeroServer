package com.thirtygames.zero.common.model.qatool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.generic.GenericModel;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class StageClearTool extends GenericModel {

	String accountId;
	Integer clearMode;
	Integer chapter;
	Integer stage;


}
