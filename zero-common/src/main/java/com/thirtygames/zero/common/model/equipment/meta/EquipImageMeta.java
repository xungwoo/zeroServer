package com.thirtygames.zero.common.model.equipment.meta;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
public class EquipImageMeta extends GridFilter {
	private static final long serialVersionUID = 5867667720905133320L;
	
	Integer equipmentType;
	Integer grade;
	Integer eqClass;
	Integer subCategory;
	Integer category;
	Integer dropRate;
	Integer setKey;
	String image;
	String name;
	
	Float statGrowth;
	Integer sellPrice;
	
	List<EquipStatMeta> stats;
	
	//params
	String lang;
}
