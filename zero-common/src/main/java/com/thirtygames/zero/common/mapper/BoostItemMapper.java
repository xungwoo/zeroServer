package com.thirtygames.zero.common.mapper;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.BoostItem;

public interface BoostItemMapper extends GenericMapper<BoostItem, String> {

	BoostItem getByBoostType(@Param("accountId") String accountId, @Param("boostType")int boostType);

}