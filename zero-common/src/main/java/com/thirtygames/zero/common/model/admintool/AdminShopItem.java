package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminShopItem extends GridFilter {

	Integer itemKey;
	Integer itemCategory;	// Client Tab
	Integer itemType;
	String productId;	// HSP productId;
	String itemId;	// HSP itemId;

	Integer repeatType;
	
	Integer addRewardType1;
	Long addReward1;
	Integer addRewardType2;
	Long addReward2;
	Integer addRewardType3;
	Long addReward3;
	Integer addRewardType4;
	Long addReward4;
	
	String itemImageName;
	Long itemQuantity;
	Long itemQuantityBonus;
	String bonusText;
	
	Integer buyType;	// 재화
	Long price;		// 소모재화 수량
	
	Integer sort;
	
	Boolean isDel;
	
	String lang;
	
	String modYmdt;
	String modId;
	
	String nameKey;
	String itemNameKo;
	String itemDescKo;
	String itemNameEn;
	String itemDescEn;
	

}
