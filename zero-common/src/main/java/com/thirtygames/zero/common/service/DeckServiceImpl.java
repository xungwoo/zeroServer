package com.thirtygames.zero.common.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Splitter;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.DeckMapper;
import com.thirtygames.zero.common.model.Deck;
import com.thirtygames.zero.common.model.DeckInfo;
import com.thirtygames.zero.common.model.Unit;

@Slf4j
@Service("deckService")
public class DeckServiceImpl extends GenericServiceImpl<DeckMapper, Deck, String> implements DeckService {
	
	@Autowired
	DeckInfoService deckInfoService;
	
	@Autowired
	UnitService unitService;

	@Override
	@Transactional
	public int teamUpdate(Deck deck)  {
		int result = 0;
		String accountId = deck.getAccountId();
		Integer teamId = deck.getTeamId();
		
		List<String> unitIdList = Splitter.on(':').trimResults().splitToList(deck.getUnitIds());
		log.debug("unitIdList::" + unitIdList);
		
		int index = 1;
		for (String unitId : unitIdList) {
			log.debug("unitId::" + unitId);
			Deck deckParams = new Deck();
			deckParams.setAccountId(accountId);
			deckParams.setTeamId(teamId);
			deckParams.setPositionId(index);
			deckParams.setUnitId(unitId);		
			result += mapper.save(deckParams);
			index++;
		}
		
		Deck searchDeck = new Deck();
		searchDeck.setAccountId(accountId);
		List<Unit> resultList = unitService.getDeckUnitList(accountId, unitIdList);
		
		int levelSum = 0;
		int skillLvSum = 0;
		String deckUnitTypeLevelStr = "";
		for (Unit deckUnit : resultList) {
			levelSum += deckUnit.getLevel();
			skillLvSum += deckUnit.getSkill0Lv() + deckUnit.getSkill1Lv() + deckUnit.getSkill2Lv() + deckUnit.getSkill3Lv();
			deckUnitTypeLevelStr += deckUnit.getDeckUnitTypeLevel() + ",";
		}
		
		int eqPoint = unitService.getEquipmentPoint(accountId);
		if (deckUnitTypeLevelStr.length() > 0) {
			deckUnitTypeLevelStr = deckUnitTypeLevelStr.substring(0, deckUnitTypeLevelStr.length() - 1);
		}
		
		DeckInfo deckInfo = new DeckInfo();
		deckInfo.setAccountId(accountId);
		deckInfo.setUnitLevelSum(levelSum);
		deckInfo.setSkillLevelSum(skillLvSum);
		deckInfo.setTotalLevelSum(levelSum + skillLvSum);
		deckInfo.setEquipmentPoint(eqPoint);
		deckInfo.setDeckUnitTypeLevelStr(deckUnitTypeLevelStr);
		deckInfoService.save(deckInfo);
		return result;
	}

	@Override
	public List<String> getDeckUnitStrList(String accountId) {
		return mapper.getDeckUnitStrList(accountId);
	}

	@Override
	public List<Deck> getDeckUnitList(String accountId) {
		return mapper.getDeckUnitList(accountId);
	}

}
