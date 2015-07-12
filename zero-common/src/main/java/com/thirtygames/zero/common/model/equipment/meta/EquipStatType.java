package com.thirtygames.zero.common.model.equipment.meta;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.base.CharMatcher;
import com.thirtygames.zero.common.model.params.grid.GridFilter;


@Data
@EqualsAndHashCode(callSuper = false)
public class EquipStatType extends GridFilter {
	private static final long serialVersionUID = -4990841306025882106L;
	
	Integer typeKey;
	String statEngName;
	Integer statType;
	
	public static String getStatTypeStr(List<EquipStatType> equipStatTypeList) {
		String resultStr = "";
		for(EquipStatType statType : equipStatTypeList) {
			resultStr += statType.getStatType() + ":" + statType.getStatEngName() + ";";
		}	
		
		return CharMatcher.anyOf(";").trimFrom(resultStr);
	}
}
