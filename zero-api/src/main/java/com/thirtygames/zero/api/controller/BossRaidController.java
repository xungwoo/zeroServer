package com.thirtygames.zero.api.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.model.BossRaid;
import com.thirtygames.zero.common.model.BossRaid.BossRaidStatus;
import com.thirtygames.zero.common.model.Friend;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.FriendService;
import com.thirtygames.zero.common.service.bossraid.BossRaidGuestService;
import com.thirtygames.zero.common.service.bossraid.BossRaidHostService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;
import com.thirtygames.zero.common.service.equipment.meta.EquipMetaService;
import com.thirtygames.zero.common.service.meta.BossRaidMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/boss-raids")
public class BossRaidController {

	
	@Autowired
	BossRaidHostService hostService;
	
	@Autowired
	BossRaidGuestService guestService;
	
	@Autowired
	BossRaidMetaService metaService;
	
	@Autowired
	AccountResourceService arService;
	
	@Autowired
	FriendService friendService;
	
	
	@Autowired
	EquipMetaService eqMetaService;	
	
	@Autowired
	DataSourceService dsManager;
	
	
	
	@RequestMapping(method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<BossRaid> search(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp)  {
		List<BossRaid> resultList = new ArrayList<BossRaid>();
		
		dsManager.switchDataSource(myAccountId);
		List<BossRaid> myHostList = hostService.getLiveHost(myAccountId);
		if (!myHostList.isEmpty()) {
			resultList.addAll(myHostList);
		}
		
		List<BossRaid> myLiveEventHostList = hostService.getLiveEventHost(myAccountId);
		if (!myLiveEventHostList.isEmpty()) {
			resultList.addAll(myLiveEventHostList);
		}
		
		List<String> myEventBossList = hostService.getEventBossList(myAccountId);
		List<BossRaid> eventMetaList = metaService.getEventBossRaidList(myEventBossList);
		if (!eventMetaList.isEmpty()) {
			Iterator<BossRaid> itEventMeta = eventMetaList.iterator();
			while(itEventMeta.hasNext()) {
				BossRaid eventMeta = itEventMeta.next();
				resultList.add(hostService.eventBossFind(myAccountId, eventMeta));
			}
		}
		
		List<Friend> memberNoList = friendService.getFriendMemberNoList(myAccountId);
		if (!memberNoList.isEmpty()) {
			for(DataSource dst : DataSource.GAME_SHARDS) {
				dsManager.switchDataSource(dst);
				List<BossRaid> bossRaidList = hostService.getFriendBossRaidListByMemberNoList(memberNoList);
				if (!bossRaidList.isEmpty()) {
					resultList.addAll(bossRaidList);
				}
			}
		}
		
		List<String> bossRaidIdList = guestService.getNoRewardRaidIdList(myAccountId);
		if (!bossRaidIdList.isEmpty()) {
			for(DataSource dst : DataSource.GAME_SHARDS) {
				dsManager.switchDataSource(dst);
				List<BossRaid> noRewardList = hostService.getNoRewardRaidList(bossRaidIdList);
				if (!noRewardList.isEmpty()) {
					resultList.addAll(noRewardList);
				}
			}
		}
		
		ApiJsonResult<BossRaid> result = new ApiJsonResult<BossRaid>();
		result.setResultCount(resultList.size());		
		result.setResult(resultList);
		return result;		
	}
	
	@RequestMapping(value="/find", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<BossRaid> find(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required = true, value = "bossRaidMetaId") String bossRaidMetaId,
			@RequestParam(required = true, value = "bossUnitLevelMin") int bossUnitLevelMin,
			@RequestParam(required = true, value = "bossUnitLevelMax") int bossUnitLevelMax)  {
		
		dsManager.switchDataSource(myAccountId);
		List<BossRaid> myHostList = hostService.getLiveHost(myAccountId);
		if (!myHostList.isEmpty()) {
			throw new RestException(Errors.AlreadyFoundBossRaid);
		}
		
		BossRaid bossRaidMeta = metaService.get(bossRaidMetaId);
		ValidationUtil.isNullModel(bossRaidMeta, "bossRaidMetaId:" + bossRaidMetaId);
		log.debug("bossRaidMeta::" + bossRaidMeta);
		
		ApiJsonResult<BossRaid> result = new ApiJsonResult<BossRaid>();
		result.setResult(hostService.find(myAccountId, bossUnitLevelMin, bossUnitLevelMax, bossRaidMeta));
		return result;
	}
	
	
	
	@RequestMapping(value="/join", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<BossRaid> join(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required = true, value = "bossRaidId") String bossRaidId,
			@RequestParam(required = true, value = "foundAccountId") String foundAccountId)  {
		
		ApiJsonResult<BossRaid> result = new ApiJsonResult<BossRaid>();
		
		dsManager.switchDataSource(foundAccountId);
		BossRaid hostRaid = hostService.getHostRaidInfo(bossRaidId);
		ValidationUtil.isNullModel(hostRaid, "bossRaidId:" + bossRaidId + ", foundAccountId:" + foundAccountId);
		if (hostRaid.getIsOverEndTime()) {
			throw new RestException(Errors.FinishBossRaid);
		}
		
		if (BossRaidStatus.RewardCheck.getCode() == hostRaid.getBossRaidStatus() || BossRaidStatus.RaidFinish.getCode() == hostRaid.getBossRaidStatus()) {
			throw new RestException(Errors.FinishBossRaid);
		}
		
		dsManager.switchDataSource(foundAccountId);
		hostService.join(myAccountId, bossRaidId);
		
		dsManager.switchDataSource(myAccountId);
		result.setResult(guestService.getGuestRaidInfo(myAccountId, bossRaidId));
		return result;
	}
	
	@RequestMapping(value="/clear", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<BossRaid> clear(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required = true, value = "bossRaidId") String bossRaidId,
			@RequestParam(required = true, value = "foundAccountId") String foundAccountId,
			@RequestParam(required = true, value = "damage") int damage, 
			@RequestParam(required = true, value = "isClear") boolean isClear)  {
		
		ApiJsonResult<BossRaid> result = new ApiJsonResult<BossRaid>();
		dsManager.switchDataSource(myAccountId);
		BossRaid guestRaid = guestService.getGuestRaidInfo(myAccountId, bossRaidId);
		if (guestRaid == null || !guestRaid.getIsPlay()) {
			throw new RestException(Errors.NotJoinedBossRaid);
		}
		
		dsManager.switchDataSource(foundAccountId);
		hostService.damageBoss(myAccountId, bossRaidId, isClear, damage);
		
		dsManager.switchDataSource(foundAccountId);
		result.setResult(hostService.getHostRaidInfo(bossRaidId));
		return result;
	}
	
	@RequestMapping(value="/reward", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<BossRaid> reward(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required = true, value = "bossRaidId") String bossRaidId,
			@RequestParam(required = true, value = "foundAccountId") String foundAccountId)  {
		
		dsManager.switchDataSource(foundAccountId);
		BossRaid hostRaid = hostService.getHostRaidInfo(bossRaidId);
		ValidationUtil.isNullModel(hostRaid, "bossRaidId:" + bossRaidId + ", foundAccountId:" + foundAccountId);
		if (BossRaidStatus.RaidFinish.getCode() != hostRaid.getBossRaidStatus()) {
			throw new RestException(Errors.NotFinishBossRaid, "bossRaidId:" + bossRaidId);
		}
		
		dsManager.switchDataSource(myAccountId);
		BossRaid guestRaid = guestService.getGuestRaidInfo(myAccountId, bossRaidId);
		ValidationUtil.isNullModel(guestRaid, "bossRaidId:" + bossRaidId);
		if(guestRaid.getIsRewarded()) {
			throw new RestException(Errors.AlreadyRewardedBossRaid, "bossRaidId:" + bossRaidId);
		}
		
		Map<String, Object> rewardResult = guestService.reward(guestRaid, hostRaid);
		
		ApiJsonResult<BossRaid> result = new ApiJsonResult<BossRaid>();
		result.setResult(rewardResult);
		result.setResource(arService.get(myAccountId));
		return result;
	}	
	
	
	
	@RequestMapping(value="/results", method = { RequestMethod.PUT })
	public @ResponseBody
	Map<String, Object> result(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required = true, value = "bossRaidId") String bossRaidId,
			@RequestParam(required = true, value = "foundAccountId") String foundAccountId)  {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		dsManager.switchDataSource(foundAccountId);
		BossRaid hostResult = hostService.getHostResult(bossRaidId);
		ValidationUtil.isNullModel(hostResult, "bossRaidId::" + bossRaidId);
		
		dsManager.switchDataSource(foundAccountId);
		hostService.updateRaidStatus(myAccountId, bossRaidId, hostResult.getBossRaidMetaId(), hostResult.getIsEvent());
		
		List<BossRaid> raidResultList = Lists.newArrayList();
		for(DataSource dst : DataSource.GAME_SHARDS) {
			dsManager.switchDataSource(dst);
			List<BossRaid> resultList = guestService.getGuestResultList(bossRaidId);
			if (!resultList.isEmpty()) {
				raidResultList.addAll(resultList);
			}
		}		
		
		if (!raidResultList.isEmpty()) {
			int totalDamage = hostResult.getTotalDamage();
			if (totalDamage <= hostResult.getBossMaxHp()) {
				totalDamage = hostResult.getBossMaxHp();
			}

			long goldReward = hostResult.getGoldReward();
			
			Iterator<BossRaid> itRaidResultPercent = raidResultList.iterator();
			while(itRaidResultPercent.hasNext()) {
				BossRaid raidResult = itRaidResultPercent.next();
				
				double damagePercent = guestService.getDamagePercent(totalDamage, raidResult.getDamageSum()); 
				raidResult.setDamagePercent(damagePercent);
				
				long gold = guestService.getRewardGold(goldReward, damagePercent);
				raidResult.setGoldReward(gold);
			}
			
			Ordering<BossRaid> byDamageSum = new Ordering<BossRaid>() {
	            @Override
	            public int compare(BossRaid left, BossRaid right) {
	                return Ints.compare(left.getDamageSum(), right.getDamageSum());
	            }
	        };		
			         
			Collections.sort(raidResultList, byDamageSum);
		}
		
		resultMap.put("founder", hostResult);
		resultMap.put("result", raidResultList);
		return resultMap;
	}
}
