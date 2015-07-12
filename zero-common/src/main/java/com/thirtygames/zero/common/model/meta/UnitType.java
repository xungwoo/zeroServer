package com.thirtygames.zero.common.model.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UnitType implements Serializable {
	private static final long serialVersionUID = 2678104265248915276L;
	
	Integer typeKey;
	String typeName;
	Integer typeCode;
	
}
