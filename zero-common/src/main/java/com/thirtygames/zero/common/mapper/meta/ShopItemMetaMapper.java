package com.thirtygames.zero.common.mapper.meta;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.meta.ShopItem;

public interface ShopItemMetaMapper extends GenericMapper<ShopItem, Integer> {

	public ShopItem getByItemId(@Param("itemId")String itemId);

}