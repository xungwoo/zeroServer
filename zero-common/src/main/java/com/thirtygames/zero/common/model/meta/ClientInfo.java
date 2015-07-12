package com.thirtygames.zero.common.model.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ClientInfo extends GridFilter {
	
	String clientKey;
	String clientVersion;
	String clientPlatform;
	String clientHash;
	Boolean isValid;
	
}
