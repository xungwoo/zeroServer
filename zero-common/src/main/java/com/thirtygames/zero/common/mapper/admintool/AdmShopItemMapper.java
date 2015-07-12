package com.thirtygames.zero.common.mapper.admintool;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.admintool.AdminShopItem;


public interface AdmShopItemMapper extends GenericMapper<AdminShopItem, String> {

	public int saveName(AdminShopItem shopItem);

	void updateName(AdminShopItem shopItem);

	void deleteName(String id);
}