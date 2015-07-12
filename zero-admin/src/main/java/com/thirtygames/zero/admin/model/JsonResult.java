package com.thirtygames.zero.admin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thirtygames.zero.common.generic.GenericModel;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class JsonResult extends GenericModel {
	private static final long serialVersionUID = 2850797522028512410L;
	
	boolean isSuccess;
	String resultMessage;
	
}


