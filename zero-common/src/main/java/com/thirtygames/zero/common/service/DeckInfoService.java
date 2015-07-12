package com.thirtygames.zero.common.service;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.DeckInfo;

public interface DeckInfoService extends GenericService<DeckInfo, String> {

	DeckInfo getWithAccount(String accountId);
}