package com.thirtygames.zero.common.model.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class MetaRevision extends GridFilter {

	String revisionKey;
	String metaName;
	String metaUrl;
	Integer revision;
	
	String modYmdt;
	String modId;
	

}
