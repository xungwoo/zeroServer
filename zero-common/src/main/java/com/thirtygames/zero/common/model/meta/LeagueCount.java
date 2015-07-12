package com.thirtygames.zero.common.model.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class LeagueCount extends GridFilter {
	private static final long serialVersionUID = -2513643933648132172L;
	
	Integer countKey;
	Integer league;
	Integer count;
	String resetDate;	
	
}
