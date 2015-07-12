package com.thirtygames.zero.common.service;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.Deck;

public interface DeckService extends GenericService<Deck, String> {

	public int teamUpdate(Deck deck) ;

	public List<String> getDeckUnitStrList(String accountId);

	public List<Deck> getDeckUnitList(String accountId);
	
}