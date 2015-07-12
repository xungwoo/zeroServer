package com.thirtygames.zero.common.service;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.meta.ShopItem;

public interface ShopItemService extends MetaService<ShopItem, Integer> {

	public AccountResource buyResource(ShopItem itemMeta);
	
	public void buyGems(ShopItem itemMeta, List<Equipment> gemList);
	public void buyEquips(ShopItem itemMeta, List<Equipment> eqList);

	public void buyItemBoost(ShopItem itemMeta);

	public void buyGoldBoost(ShopItem itemMeta);
	
}