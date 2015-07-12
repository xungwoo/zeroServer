package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminApiMeta extends GridFilter {
	
	String metaKey;
	String name;
	String value;
	Long longValue;
	Float floatValue;
	String description;
	
	String modYmdt;
	String modId;
	
}
