package com.thirtygames.zero.common.model.equipment.meta;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.base.CharMatcher;
import com.thirtygames.zero.common.model.params.grid.GridFilter;


@Data
@EqualsAndHashCode(callSuper = false)
public class EquipType extends GridFilter {
	private static final long serialVersionUID = -4990841306025882106L;
	
	Integer typeKey;
	String typeName;
	Integer typeCode;
	String categoryName;
	Integer categoryCode;
	String subCategoryName;
	Integer subCategoryCode;
	Integer hasMeta;
	
	public String getHasMeta() {
		String str = "N";
		if (this.hasMeta != null && this.hasMeta > 0 ) {
			str = "Y";
		}
		return str;
	}
	
	public static String getCategoryStr(List<EquipType> equipTypeList) {
		String resultStr = "";
		for(EquipType equipType : equipTypeList) {
			resultStr += equipType.getCategoryCode() + ":" + equipType.getCategoryName() + ";";
		}	
		
		return CharMatcher.anyOf(";").trimFrom(resultStr);
	}
	
	public static String getGemStatTypeStr(List<EquipType> gemStatTypeList) {
		String resultStr = "";
		for(EquipType equipType : gemStatTypeList) {
			resultStr += equipType.getCategoryCode() + ":" + equipType.getCategoryName() + ";";
		}	
		
		return CharMatcher.anyOf(";").trimFrom(resultStr);
	}
	
	public static String getSubCategoryStr(List<EquipType> equipTypeList) {
		String resultStr = "";
		for(EquipType equipType : equipTypeList) {
			resultStr += equipType.getSubCategoryCode() + ":" + equipType.getSubCategoryName() + ";";
		}	
		
		return CharMatcher.anyOf(";").trimFrom(resultStr);
	}
	
	public static String getEquipTypeStr(List<EquipType> equipTypeList) {
		String resultStr = "";
		for(EquipType equipType : equipTypeList) {
			resultStr += equipType.getTypeCode() + ":" + equipType.getTypeName() + ";";
		}	
		
		return CharMatcher.anyOf(";").trimFrom(resultStr);
	}
	
	public static String getSubCategoryOptions(List<EquipType> equipTypeList) {
		String resultStr = "<select>";
		for(EquipType equipType : equipTypeList) {
			resultStr += "<option value='" + equipType.getSubCategoryCode() + "'>" + equipType.getSubCategoryName() + "</option>";
		}	
		resultStr += "</select>";
		
		return resultStr;
	}
	
	public static String getEquipTypeOptions(List<EquipType> equipTypeList) {
		String resultStr = "<select>";
		for(EquipType equipType : equipTypeList) {
			resultStr += "<option value='" + equipType.getTypeCode() + "'>" + equipType.getTypeName() + "</option>";
		}	
		resultStr += "</select>";		
		
		return resultStr;
	}
}
