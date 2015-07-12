package com.thirtygames.zero.common.model.params.grid;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.CharMatcher;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GridFilter {
	
	String groupOp;
	List<FilterRules> rules;
	
	String name;
	Integer code;
	public static String getCodeNameStr(List<? extends GridFilter> codeNameList) {
		String resultStr = "";
		for(GridFilter model : codeNameList) {
			resultStr += model.getCode() + ":" + model.getName() + ";";
		}	
		
		return CharMatcher.anyOf(";").trimFrom(resultStr);
	}	
}
