package com.thirtygames.zero.common.model.common;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.generic.GenericModel;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResourceModel extends GenericModel implements Serializable {
	private static final long serialVersionUID = -7143953551073464299L;
	
	
	Long useGold;
	Long useCash;

}
