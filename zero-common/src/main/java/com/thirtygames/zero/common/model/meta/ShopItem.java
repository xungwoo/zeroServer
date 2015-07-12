package com.thirtygames.zero.common.model.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ShopItem implements Cloneable, Serializable {
	private static final long serialVersionUID = -1789167654129050387L;
	
	// Params
	String accountId;
	
	Integer itemKey;
	Integer itemCategory;	// Client Tab
	Integer itemType;
	
	Integer repeatType;
	
	Integer addRewardType1;
	Long addReward1;
	Integer addRewardType2;
	Long addReward2;
	Integer addRewardType3;
	Long addReward3;
	Integer addRewardType4;
	Long addReward4;
	
	String productId;	// HSP productId;
	String itemId;	// HSP itemId;
	
	String itemName;
	String itemDesc;
	String itemImageName;
	Long itemQuantity;
	Long itemQuantityBonus;
	String bonusText;
	
	Integer buyType;	// 재화
	Long price;		// 소모재화 수량
	
	Integer sort;
	
	String lang;
	
	
	Long repeatStartYmdt;
	Long repeatEndYmdt;
	

	public ShopItem clone() throws CloneNotSupportedException {
		ShopItem shopItem = (ShopItem) super.clone();
		return shopItem;
	}	
	
	@RequiredArgsConstructor
	public enum RepeatType {
		None(0, 0), Cash30(1, 30);

		@Getter
		private final int code;
		
		@Getter
		private final int count;

		@Getter
		private static final java.util.Map<java.lang.Integer, RepeatType> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, RepeatType>();
		static {
			for (RepeatType status : RepeatType.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}

		public static RepeatType findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'RepeatType' has no value for 'code = %s'", code));
		}
	}
	
	// Client ShopItemCategory
	@RequiredArgsConstructor
	public enum ItemCategory {
		Play(0), Unit(1), Equipment(2), Gem(3), Gold(4), Cash(5), Etc(6);

		@Getter
		private final int code;

		@Getter
		private static final java.util.Map<java.lang.Integer, ItemCategory> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, ItemCategory>();
		static {
			for (ItemCategory status : ItemCategory.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}

		public static ItemCategory findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'ItemCategory' has no value for 'code = %s'", code));
		}
	}	
	
	@RequiredArgsConstructor
	public enum ItemType {
		AP(0), BP(1), UnlockKey(2), Gold(3), Equipment(5), Gem(6), Cash(7), EquipLevelUpDrug(8), GoldBoost(9), ItemBoost(10);
		
		@Getter
		private final int code;
		
		@Getter
		private static final java.util.Map<java.lang.Integer, ItemType> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, ItemType>();
		static {
			for (ItemType status : ItemType.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static ItemType findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'ItemType' has no value for 'code = %s'", code));
		}
	}

}
