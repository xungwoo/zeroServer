package com.thirtygames.zero.api.controller;

import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.BossCollection;
import com.thirtygames.zero.common.model.BossRaid;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.bossraid.BossCollectionService;
import com.thirtygames.zero.common.service.bossraid.BossRaidGuestService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;
import com.thirtygames.zero.common.service.meta.BossCollectionMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/boss-raids/collections")
public class BossCollectionController {

	
	@Autowired
	BossCollectionService service;
	
	@Autowired
	BossRaidGuestService bossRaidGuestService;
	
	@Autowired
	BossCollectionMetaService metaService;
	
	@Autowired
	AccountResourceService arService;
	
	@Autowired
	DataSourceService dsManager;
	
	
	
	@RequestMapping(value = "/langs/{lang}", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<BossRaid> list(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@PathVariable("lang") String lang)  {
		
		
		List<BossCollection> metaList = metaService.getCollectionMetaList(lang);
		List<String> clearBossRaidMetaIdList = bossRaidGuestService.getClearBossRaidMetaIds(myAccountId);
		List<BossCollection> rewardInfoOfCollections = service.getRewardInfoOfBossCollections(myAccountId);
		
		Iterator<BossCollection> itMeta = metaList.iterator();
		while(itMeta.hasNext()) {
			BossCollection collectionMeta = itMeta.next();
			collectionMeta.setIsRewarded(false);
			collectionMeta.setIsComplete(false);
			
			List<BossRaid> bossRaidList = Lists.newArrayList();
			List<String> idList = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(collectionMeta.getBossMetaIds());
			Iterator<String> itMetaId = idList.iterator();
			while(itMetaId.hasNext()) {
				String metaId = itMetaId.next();
				if (clearBossRaidMetaIdList.contains(metaId)) {
					BossRaid bossRaid = new BossRaid();
					bossRaid.setBossRaidMetaId(metaId);
					bossRaid.setIsClear(true);
					bossRaidList.add(bossRaid);
				}
			}
			collectionMeta.setBossRaidList(bossRaidList);
			if (idList.size() == bossRaidList.size()) {
				collectionMeta.setIsComplete(true);
			}
			
			Iterator<BossCollection> itRewardInfo = rewardInfoOfCollections.iterator();
			while(itRewardInfo.hasNext()) {
				BossCollection rewardInfo = itRewardInfo.next();
				if (rewardInfo.getCollectionId() == collectionMeta.getCollectionId()) {
					collectionMeta.setIsRewarded(rewardInfo.getIsRewarded());
					itRewardInfo.remove();
				}
			}
			
		}
		
		ApiJsonResult<BossRaid> result = new ApiJsonResult<BossRaid>();
		result.setResult(metaList);
		return result;		
	}
	
	
	@RequestMapping(value="/reward", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<BossRaid> reward(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required = true, value = "collectionId") String collectionId)  {
		
		BossCollection collectionMeta = metaService.get(collectionId);
		List<String> idList = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(collectionMeta.getBossMetaIds());
		if (!service.isCompleteCollection(myAccountId, idList)) {
			throw new RestException(Errors.NotCompleteBossCollection);
		}
		
		service.reward(myAccountId, collectionMeta);
		
		ApiJsonResult<BossRaid> result = new ApiJsonResult<BossRaid>();
		result.setResult(collectionMeta);
		result.setResource(arService.get(myAccountId));
		return result;
	}		
	
	
}
