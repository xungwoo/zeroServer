package com.thirtygames.zero.common.model.log;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ShopItemLog extends GridFilter implements Serializable {

	Integer logKey;
	String accountId;
	String productId;
	Integer itemKey;
	Integer itemCategory;	// Client Tab
	Integer itemType;
	String itemId;	// HSP itemId;
	Long itemQuantity;
	Long itemQuantityBonus;
	Integer buyType;	// 재화
	Long price;		// 소모재화 수량
	
	Date logYmdt;
}
