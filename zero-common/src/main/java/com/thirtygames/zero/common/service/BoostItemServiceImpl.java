package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.BoostItemMapper;
import com.thirtygames.zero.common.model.BoostItem;

@Slf4j
@Service("boostItemService")
public class BoostItemServiceImpl extends GenericServiceImpl<BoostItemMapper, BoostItem, String> implements BoostItemService {@Override
	public BoostItem getByBoostType(String accountId, int boostType) {
		return mapper.getByBoostType(accountId, boostType);
	}

}