package com.thirtygames.zero.common.service.bossraid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.LanguageCode;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.BossRaidHostMapper;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.BossRaid;
import com.thirtygames.zero.common.model.BossRaid.BossRaidStatus;
import com.thirtygames.zero.common.model.BossRaid.BossRaidTimeStatus;
import com.thirtygames.zero.common.model.Friend;
import com.thirtygames.zero.common.service.AccountDynamicService;
import com.thirtygames.zero.common.service.AccountService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;
import com.thirtygames.zero.common.service.equipment.meta.EquipMetaService;
import com.thirtygames.zero.common.service.hsp.HSPPushManager;
import com.thirtygames.zero.common.service.hsp.PushMessage;
import com.thirtygames.zero.common.service.hsp.PushMessage.PushType;
import com.thirtygames.zero.common.service.meta.BossRaidMetaService;

@Slf4j
@Service("bossRaidHostService")
public class BossRaidHostServiceImpl extends GenericServiceImpl<BossRaidHostMapper, BossRaid, String> implements BossRaidHostService {
	
	
	@Autowired
	BossRaidMetaService metaService;
	
	@Autowired
	EquipMetaService eqMetaService;
	
	@Autowired
	BossRaidGuestService guestService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountDynamicService accountDynamicService;
	
	@Autowired
	DataSourceService dsManager;
	
	@Autowired
	HSPPushManager pushManager;
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)		
	public List<BossRaid> getLiveHost(String accountId) {
		return mapper.getLiveHost(accountId);
	}


	@Override
	@Transactional
	public BossRaid find(String accountId, int bossUnitLevelMin, int bossUnitLevelMax, BossRaid bossRaidMeta) {
		BossRaid foundRaid = new BossRaid();

		float exposeRate = bossRaidMeta.getExposeRate();
		Random r = new Random();
		float randomFloat = r.nextFloat();
		log.debug("randomFloat::" + randomFloat);
		log.debug("exposeRate::" + exposeRate);
		
		if (randomFloat < exposeRate) {
			BossRaid bossRaid = new BossRaid();
			bossRaid.setAccountId(accountId);
			
			String bossRaidId = UUID.randomUUID().toString();
			bossRaid.setBossRaidId(bossRaidId);
			bossRaid.setBossRaidMetaId(bossRaidMeta.getBossRaidMetaId());
			bossRaid.setBossRaidStatus(BossRaid.BossRaidStatus.Find.getCode());
			bossRaid.setCountPartyFriend(0);
			bossRaid.setPlayTime(bossRaidMeta.getPlayTime());
			bossRaid.setIsClear(false);
			bossRaid.setIsEvent(false);
			
			Random levelRandom = new Random();
			int bossUnitLevel = levelRandom.nextInt((bossUnitLevelMax - bossUnitLevelMin) + 1) + bossUnitLevelMin;
			bossRaid.setBossUnitLevel(bossUnitLevel);
			
			double bossHpScaler = 1d; 
			for (int i=1; i<=bossUnitLevel; i++) {
				bossHpScaler = bossHpScaler * bossRaidMeta.getBossHpScaler(); 
			}
				
			int bossMaxHp = (int) (bossRaidMeta.getBossHpFirst() * bossHpScaler / 100) * 100;
			bossRaid.setBossMaxHp(bossMaxHp);
			bossRaid.setBossNowHp(1d);
			
			int difficulty = (int) Math.ceil((float) bossUnitLevel / bossRaidMeta.getGradeRatio());
			if (difficulty < 1) difficulty = 1; 
			if (difficulty > BossRaid.MAX_DIFFICULTY) difficulty = BossRaid.MAX_DIFFICULTY; 
				
			bossRaid.setDifficulty(difficulty);
			
			double goldRewardScaler = 1d; 
			for (int i=1; i<=bossUnitLevel; i++) {
				goldRewardScaler = goldRewardScaler * bossRaidMeta.getGoldRewardScaler(); 
			}
			
			long goldReward = (long) (bossRaidMeta.getGoldRewardFirst() * goldRewardScaler / 100) * 100;
			bossRaid.setGoldReward(goldReward);
			
			int equipmentType = eqMetaService.getBossRaidEquipReward(bossRaidMeta.getFounderEquipCategory(), bossRaidMeta.getGradeRatio(), bossUnitLevel);
			bossRaid.setEquipmentReward(equipmentType);
			mapper.save(bossRaid);
			
			
			mapper.incPartyCount(bossRaidId);
		
			dsManager.switchDataSource(accountId);
			BossRaid guestRaid = new BossRaid();
			guestRaid.setBossRaidId(bossRaidId);
			guestRaid.setAccountId(accountId);
			guestRaid.setBossRaidMetaId(bossRaidMeta.getBossRaidMetaId());
			guestRaid.setFoundAccountId(accountId);
			guestRaid.setIsPlay(false);
			guestService.save(guestRaid);
			
			foundRaid = mapper.get(bossRaidId);
		}		
		
		return foundRaid;
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)		
	public BossRaid getHostRaidInfo(String bossRaidId) {
		return mapper.getHostRaidInfo(bossRaidId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)	
	public BossRaid join(String accountId, String bossRaidId) {
		BossRaid hostRaid = mapper.getHostRaidInfo(bossRaidId);
		ValidationUtil.isNullModel(hostRaid, "bossRaidId:" + bossRaidId);		
		
		if (BossRaidStatus.Find.getCode() != hostRaid.getBossRaidStatus() && BossRaidStatus.Start.getCode() != hostRaid.getBossRaidStatus()) {
			throw new RestException(Errors.FinishBossRaid);
		}		
		
		dsManager.switchDataSource(accountId);
		BossRaid guestBossRaid = guestService.getGuestRaidInfo(accountId, bossRaidId);
		if (guestBossRaid == null) {
			if (hostRaid.getCountPartyFriend() >= BossRaid.MAX_COUNT_OF_PARTY) {
				throw new RestException(Errors.FullMemberBossRaid);
			}
			
			mapper.incPartyCount(bossRaidId);
		}
		
		dsManager.switchDataSource(accountId);
		BossRaid guestRaid = new BossRaid();
		guestRaid.setBossRaidId(bossRaidId);
		guestRaid.setAccountId(accountId);
		guestRaid.setBossRaidMetaId(hostRaid.getBossRaidMetaId());
		guestRaid.setFoundAccountId(hostRaid.getAccountId());
		guestRaid.setIsPlay(true);
		guestService.save(guestRaid);
		return guestRaid;
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)	
	public BossRaid damageBoss(String accountId, String bossRaidId, boolean isClear, int damage) {
		BossRaid hostRaid = mapper.getHostRaidInfo(bossRaidId);
		ValidationUtil.isNullModel(hostRaid, "bossRaidId:" + bossRaidId);
		int bossRaidStatus = this.getRaidStatusByTimeCheck(bossRaidId, hostRaid.getBossRaidStatus());
		
		double bossNowHp = 0d;
		double bossMaxHp = hostRaid.getBossMaxHp();
		double damagePercent = ((double) damage) / bossMaxHp;
		log.debug("pre bossNowHp::" + hostRaid.getBossNowHp());
		log.debug("damagePercent::" + damagePercent);
		
		if (!isClear) {
			bossNowHp = hostRaid.getBossNowHp() - damagePercent;
			if (bossNowHp <= 0d) {
				isClear = true;
			}
		}
		
		if (isClear) {
			log.debug("pre damage::" + damage);
			log.debug("bossNowHp::" + bossNowHp);
			log.debug("bossMaxHp::" + bossMaxHp);
			log.debug("post damage::" + damage);
			
			log.debug("############ 1. Boss Clear");
			bossRaidStatus = BossRaidStatus.RewardCheck.getCode();
			
			int playUserCount = 0;
			List<BossRaid> guestList = Lists.newArrayList();
			for(DataSource dst : DataSource.GAME_SHARDS) {
				dsManager.switchDataSource(dst);
				List<BossRaid> list = guestService.getGuestList(bossRaidId);
				guestList.addAll(list);
			}
			
			List<Long> memberNoList = Lists.newArrayList();
			for(BossRaid guest : guestList) {
				memberNoList.add(guest.getMemberNo());
				if (!accountId.equals(guest.getAccountId()) && guest.getIsPlay()) {
					playUserCount++;
				}
			}
			
			if (playUserCount <= 0) {
				log.debug("############ 2. Boss Clear & PUSH !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				bossRaidStatus = BossRaidStatus.RaidFinish.getCode();
				this.sendBossClearPush(accountId, memberNoList, hostRaid.getBossRaidMetaId(), hostRaid.getIsEvent());
			}
			
			bossNowHp = 0d;
		}
		
		BossRaid updateHost = new BossRaid();
		updateHost.setIsClear(isClear);
		updateHost.setBossRaidId(bossRaidId);
		updateHost.setBossNowHp(bossNowHp);
		updateHost.setTotalDamage(damage);
		updateHost.setBossRaidStatus(bossRaidStatus);
		mapper.update(updateHost);		
		
		BossRaid updateGuest = new BossRaid();
		updateGuest.setAccountId(accountId);
		updateGuest.setBossRaidId(bossRaidId);
		updateGuest.setDamageSum(damage);
		updateGuest.setIsClear(isClear);
		updateGuest.setIsPlay(false);
		dsManager.switchDataSource(accountId);
		guestService.updateGuest(updateGuest);
		return updateGuest;
	}


	@Async
	private void sendBossClearPush(String accountId, List<Long> memberNoList, String bossRaidMetaId, Boolean isEvent) {
		String msg = this.makeBossClearMsg(accountId, bossRaidMetaId, isEvent);
		pushManager.sendGroupPush(memberNoList, msg);
	}

	private String makeBossClearMsg(String accountId, String bossRaidMetaId, Boolean isEvent) {
		dsManager.switchDataSource(accountId);
		Account account = accountDynamicService.get(accountId);
		ValidationUtil.isNullModel(account, "accountId:" + accountId);
		
		String nickName = account.getNickName();
		String lang = account.getLanguage();
		
		String title = "";
		if (isEvent) {
			BossRaid meta = metaService.getEventMetaByLang(bossRaidMetaId, lang);
			title = meta.getTitleForEvent();
		} else {
			BossRaid meta = metaService.getByLang(bossRaidMetaId, lang);
			title = meta.getTitleForEvent();
		}
		
		Object[] args = { nickName, title };
		String msg = PushMessage.MsgType.findByCode(PushType.BossClear, LanguageCode.findByCode(lang)).getTextByArgs(args);
		log.debug("######## msg::" + msg);
		try {
			msg = URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RestException(Errors.SendPushError, "URLEoncoder UnsupportedEncodingException");
		}
		
		msg = msg.replaceAll("\\+", "%20");
		log.debug("######## URLEncoder.encode msg::" + msg);
		return msg;
	}


	private int getRaidStatusByTimeCheck(String bossRaidId, int bossRaidStatus) {
		int bossRaidTimeStatus = mapper.checkTimeOver(bossRaidId);
		if (BossRaidTimeStatus.TimeOver.getCode() == bossRaidTimeStatus) {
			throw new RestException(Errors.FinishBossRaid);
		}	
		
		switch (BossRaidTimeStatus.findByCode(bossRaidTimeStatus)) {
		case NoOver:
			break;
		case OverAndWait:
			bossRaidStatus = BossRaidStatus.RewardCheck.getCode();
			break;
		case TimeOver:
			bossRaidStatus = BossRaidStatus.RaidFinish.getCode();
			break;
		}
		return bossRaidStatus;
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)		
	public BossRaid getHostResult(String bossRaidId) {
		return mapper.getHostResult(bossRaidId);
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)		
	public void updateRaidStatus(String accountId, String bossRaidId, String bossRaidMetaId, boolean isEvent) {
		int bossRaidTimeStatus = mapper.checkTimeOver(bossRaidId);
		if (bossRaidTimeStatus == BossRaidTimeStatus.TimeOver.getCode()) {
			BossRaid updateParams = new BossRaid();
			updateParams.setBossRaidId(bossRaidId);
			updateParams.setBossRaidStatus(BossRaidStatus.RaidFinish.getCode());
			mapper.update(updateParams);
			
			if (mapper.isHostClear(bossRaidId) > 0) {
				updateParams.setIsClear(true);
				updateParams.setAccountId(accountId);
				
				List<BossRaid> guestList = Lists.newArrayList();
				for(DataSource dst : DataSource.GAME_SHARDS) {
					dsManager.switchDataSource(dst);
					guestService.updateAllGuest(updateParams);
					List<BossRaid> list = guestService.getGuestList(bossRaidId);
					guestList.addAll(list);
				}			
				
				List<Long> memberNoList = Lists.newArrayList();
				for(BossRaid guest : guestList) {
					memberNoList.add(guest.getMemberNo());
				}				
				
				this.sendBossClearPush(accountId, memberNoList, bossRaidMetaId, isEvent);
			}
		}
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)		
	public List<BossRaid> getNoRewardRaidList(List<String> bossRaidIdList) {
		return mapper.getNoRewardRaidList(bossRaidIdList);
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)		
	public List<BossRaid> getFriendBossRaidListByMemberNoList(List<Friend> memberNoList) {
		return mapper.getFriendBossRaidListByMemberNoList(memberNoList);
	}


	@Override
	public List<String> getEventBossList(String accountId) {
		return mapper.getEventBossList(accountId);
	}


	@Override
	@Transactional
	public BossRaid eventBossFind(String accountId, BossRaid eventMeta) {
		BossRaid bossRaid = new BossRaid();
		bossRaid.setAccountId(accountId);
		bossRaid.setFoundAccountId(accountId);
		
		String bossRaidId = UUID.randomUUID().toString();
		bossRaid.setBossRaidId(bossRaidId);
		bossRaid.setBossRaidMetaId(eventMeta.getBossRaidMetaId());
		bossRaid.setBossRaidStatus(BossRaid.BossRaidStatus.Find.getCode());
		bossRaid.setCountPartyFriend(0);
		//bossRaid.setPlayTime(eventMeta.getPlayTime());	//TODO playTime 으로? 
		bossRaid.setIsClear(false);
		bossRaid.setIsEvent(true);
		
		int bossUnitLevel = eventMeta.getBossUnitLevel();
		bossRaid.setBossUnitLevel(bossUnitLevel);

		int difficulty = (int) Math.ceil((float) bossUnitLevel / eventMeta.getGradeRatio());
		if (difficulty < 1) difficulty = 1; 
		if (difficulty > BossRaid.MAX_DIFFICULTY) difficulty = BossRaid.MAX_DIFFICULTY; 
		bossRaid.setDifficulty(difficulty);
			
		bossRaid.setBossMaxHp(eventMeta.getBossMaxHp());
		bossRaid.setBossNowHp(1d);
		bossRaid.setGoldReward(eventMeta.getGoldReward());
		bossRaid.setEndYmdt(eventMeta.getExposeEndYmdt());
		bossRaid.setEquipmentReward(eventMeta.getFounderEquipmentType());
		mapper.save(bossRaid);
		Timestamp currentTS = new Timestamp(new java.util.Date().getTime());
		bossRaid.setStartYmdt(currentTS.getTime() / 1000);
		return bossRaid;
	}


	@Override
	public List<BossRaid> getLiveEventHost(String accountId) {
		return mapper.getLiveEventHost(accountId);
	}


	

}
