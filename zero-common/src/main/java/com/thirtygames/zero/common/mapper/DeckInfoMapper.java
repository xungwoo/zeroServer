package com.thirtygames.zero.common.mapper;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.DeckInfo;

public interface DeckInfoMapper extends GenericMapper<DeckInfo, String> {

	DeckInfo getWithAccount(@Param("accountId")String accountId);
}