package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
public class AdminEquipLevelMeta extends GridFilter {

	Integer levelMetaKey;
	Float normal;
	Float magic;
	Float rare;
	Float unique;
	Float set;
	Float consumeExpRate;
	
	Integer firstLevelUpExp;
	Double levelUpExpScale;
	Double level10ExpScale; 

	Integer firstLevelUpCost;
	Double levelUpCostScale;
	Double level10CostScale;	

	Float statGrowth;
	Integer sellPrice;
	
	Float equipLevelUpDrugRate;
	Integer equipLevelUpDrugMax;

	String modId;
	String modYmdt;
}
