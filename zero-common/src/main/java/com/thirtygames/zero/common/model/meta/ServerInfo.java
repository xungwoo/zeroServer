package com.thirtygames.zero.common.model.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ServerInfo extends GridFilter {
	
	@JsonIgnore Integer serverKey;
	String url;
	String name;
	String description;	
	
	String modYmdt;
	String modId;
	
}
