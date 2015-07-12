package com.thirtygames.zero.common.service.bossraid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.BossRaidGuestMapper;
import com.thirtygames.zero.common.model.BossRaid;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.model.meta.Reward.ReasonType;
import com.thirtygames.zero.common.service.RewardService;
import com.thirtygames.zero.common.service.equipment.meta.EquipMetaService;
import com.thirtygames.zero.common.service.meta.BossRaidMetaService;

@Slf4j
@Service("bossRaidGuestService")
public class BossRaidGuestServiceImpl extends GenericServiceImpl<BossRaidGuestMapper, BossRaid, String> implements BossRaidGuestService {
	
	
	@Autowired
	RewardService rewardService;
	
	@Autowired
	BossRaidMetaService metaService;
	
	@Autowired
	EquipMetaService eqMetaService;
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)			
	public int save(BossRaid raid)  {
		return mapper.save(raid);
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)			
	public void updateGuest(BossRaid raid) {
		mapper.update(raid);
	}	
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)		
	public BossRaid getGuestRaidInfo(String accountId, String bossRaidId) {
		return mapper.getGuestRaidInfo(accountId, bossRaidId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)		
	public List<BossRaid> getGuestResultList(String bossRaidId) {
		return mapper.getGuestResultList(bossRaidId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)	
	public int countPlayingUser(String bossRaidId) {
		return mapper.countPlayingUser(bossRaidId);
	}	
	
	@Override
	public Map<String, Object> reward(BossRaid guestRaid, BossRaid hostRaid) {
		Map<String, Object> rewardMap = new HashMap<String, Object>(); 
		String accountId = guestRaid.getAccountId();
		
		int totalDamage = hostRaid.getTotalDamage();
		int bossMaxHp = hostRaid.getBossMaxHp();
		long hostGoldReward = hostRaid.getGoldReward();
		int guestDamageSum = guestRaid.getDamageSum();
		
		if (totalDamage < bossMaxHp) {
			totalDamage = bossMaxHp;
		}
		
		double damagePercent = this.getDamagePercent(totalDamage, guestDamageSum);
		long guestRewardGold = this.getRewardGold(hostGoldReward, damagePercent);		
		
		Reward goldReward = new Reward();
		goldReward.setAccountId(accountId);
		goldReward.setReasonType(ReasonType.BossRaid.getCode());
		goldReward.setRewardType(Reward.RewardType.Gold.getCode());
		goldReward.setReward(guestRewardGold);
		rewardService.reward(goldReward, false);
		rewardMap.put("gold", goldReward);
		
		if (guestRaid.getAccountId().equals(hostRaid.getAccountId())) {
			Reward founderReward = new Reward();
			founderReward.setAccountId(accountId);
			founderReward.setReasonType(ReasonType.BossRaid.getCode());
			founderReward.setRewardType(Reward.RewardType.Equipment.getCode());
			founderReward.setReward((long) hostRaid.getEquipmentReward());
			Reward rewardEquipment = rewardService.reward(founderReward, false);
			rewardMap.put("equipment", rewardEquipment.getEquipment());
		}
		
		BossRaid updateGuest = new BossRaid();
		updateGuest.setAccountId(accountId);
		updateGuest.setBossRaidId(guestRaid.getBossRaidId());
		updateGuest.setIsRewarded(true);
		mapper.update(updateGuest);
		return rewardMap;
	}

	@Override
	public List<String> getNoRewardRaidIdList(String accountId) {
		return mapper.getNoRewardRaidIdList(accountId);
	}


	@Override
	public List<String> getClearBossRaidMetaIds(String accountId) {
		return mapper.getClearBossRaidMetaIds(accountId);
	}


	@Override
	public void updateAllGuest(BossRaid updateParams) {
		mapper.updateAllGuest(updateParams);
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)	
	public List<BossRaid> getGuestList(String bossRaidId) {
		return mapper.getGuestList(bossRaidId);
	}
	
	@Override
	public long getRewardGold(long goldReward, double damagePercent) {
		return (long) (Math.ceil(goldReward * damagePercent / 100d));
	}

	@Override
	public double getDamagePercent(int totalDamage, int damageSum) {
		double damagePercent = ((double)damageSum * 100d) / (double)totalDamage;
		return Math.round(damagePercent *100d) / 100d;
	}	
}
