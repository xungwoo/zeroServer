package com.thirtygames.zero.common.mapper;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.Deck;

public interface DeckMapper extends GenericMapper<Deck, String> {

	public List<String> getDeckUnitStrList(String accountId);
	public List<Deck> getDeckUnitList(String accountId);
	public int excludeUnit(String unitId);
	
}