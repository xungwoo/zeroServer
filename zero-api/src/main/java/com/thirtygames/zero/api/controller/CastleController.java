package com.thirtygames.zero.api.controller;

import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.Castle;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.AccountService;
import com.thirtygames.zero.common.service.CastleService;
import com.thirtygames.zero.common.service.meta.CastleMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/castle")
public class CastleController  {
	
	@Autowired
	CastleService service;
	
	@Autowired
	CastleMetaService metaService;
	
	@Autowired
	AccountResourceService arService;
	
	@Autowired
	AccountService accountService;


	@RequestMapping(method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<Castle> search(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp)  {
		
		List<Castle> metaList = metaService.range(0, 0);
		
		Castle castleParams = new Castle();
		castleParams.setAccountId(myAccountId);
		List<Castle> castleList = service.search(castleParams);
		
		List<Castle> resultList = Lists.newArrayList();
		
		Iterator<Castle> itMeta = metaList.iterator();
		while(itMeta.hasNext()) {
			Castle castleMeta = itMeta.next();
			Iterator<Castle> itCastle = castleList.iterator();
			while(itCastle.hasNext()) {
				Castle castle = itCastle.next();
				if (castleMeta.getCastleId().equals(castle.getCastleId())) {
					castleMeta.setLastClearLevel(castle.getLastClearLevel());
					itCastle.remove();
				}
			}
			resultList.add(castleMeta);
		}
		
		ApiJsonResult<Castle> result = new ApiJsonResult<Castle>();
		result.setResult(resultList);
		return result;
	}
	
	
	@RequestMapping(value = "/reward", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Castle> reward(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@RequestParam(required = true, value = "castleId") int castleId,
			@RequestParam(required = true, value = "isClear") boolean isClear,
			@RequestParam(required = true, value = "rewardGold") long rewardGold,
			@RequestParam(required = true, value = "castleLevel") int castleLevel,
			@RequestParam(required = true, value = "castleFloor") int castleFloor,
			@RequestParam(required = true, value = "castlePoint") int castlePoint)  {

		ApiJsonResult<Castle> result = new ApiJsonResult<Castle>();

		Castle castleParam = new Castle();
		castleParam.setCastleId(castleId);
		castleParam.setAccountId(myAccountId);
		castleParam.setIsClear(isClear);
		castleParam.setCastlePoint(castlePoint);
		castleParam.setCastleLevel(castleLevel);
		castleParam.setCastleFloor(castleFloor);
		
		Castle resultCastle = service.clearReward(castleParam, rewardGold);
		
		Account account = accountService.get(myAccountId);
		resultCastle.setCastlePoint(account.getCastlePoint());
		resultCastle.setCastleLastClearFloor(account.getCastleLastClearFloor());
		
		Castle castle = service.getByCastleId(myAccountId, castleId);
		int lastClearLevel = castleLevel;
		if (castle != null) {
			lastClearLevel = castle.getLastClearLevel();
		}
		resultCastle.setLastClearLevel(lastClearLevel);
		result.setResult(resultCastle);
		result.setResource(arService.get(myAccountId));
		return result;
	}

}
