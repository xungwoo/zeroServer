package com.thirtygames.zero.common.service.meta;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.meta.ShopItem;

public interface ShopItemMetaService extends MetaService<ShopItem, Integer> {

	public ShopItem getByItemId(String itemId);
	
}