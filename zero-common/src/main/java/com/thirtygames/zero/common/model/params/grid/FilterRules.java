package com.thirtygames.zero.common.model.params.grid;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FilterRules implements Serializable {
	private static final long serialVersionUID = 6380685936812266740L;
	
	String field;
	String op;
	String data;
}
