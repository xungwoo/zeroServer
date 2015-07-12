package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.DeckInfoMapper;
import com.thirtygames.zero.common.model.DeckInfo;

@Slf4j
@Service("deckInfoService")
public class DeckInfoServiceImpl extends GenericServiceImpl<DeckInfoMapper, DeckInfo, String> implements DeckInfoService {
	@Override
	public DeckInfo getWithAccount(String accountId) {
		return mapper.getWithAccount(accountId);
	}
	

}
