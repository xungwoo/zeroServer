package com.thirtygames.zero.common.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


// Client ShopItemBuyType
@RequiredArgsConstructor
public enum PriceType {
    Billing(0), Gold(1), Cash(2), FP(3), UnlockKey(4), EquipTicket(5);
    
    @Getter
	private final int code;

    @Getter
	private static final java.util.Map<java.lang.Integer, PriceType> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, PriceType>();
	static {
		for (PriceType status : PriceType.values()) {
			$CODE_LOOKUP.put(status.code, status);
		}
	}

	public static PriceType findByCode(final int code) {
		if ($CODE_LOOKUP.containsKey(code)) {
			return $CODE_LOOKUP.get(code);
		}
		throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'PriceType' has no value for 'code = %s'", code));
	}	    
}	



