package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.CastleMapper;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.Castle;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.model.meta.Reward.ReasonType;
import com.thirtygames.zero.common.model.meta.Reward.RewardType;

@Slf4j
@Service("castleService")
public class CastleServiceImpl extends GenericServiceImpl<CastleMapper, Castle, Integer> implements CastleService {
	
	@Autowired
	RewardService rewardService;
	
	@Autowired
	AccountService accountService;
	

	@Transactional
	@Override
	public Castle clearReward(Castle castle, long rewardGold) {
		Reward castleReward = new Reward();
		castleReward.setAccountId(castle.getAccountId());
		castleReward.setReasonType(ReasonType.Castle.getCode());
		castleReward.setRewardType(RewardType.Gold.getCode());
		castleReward.setReward(rewardGold);
		rewardService.reward(castleReward, false);
		
		if (castle.getIsClear()) {
			Account accountParams = new Account();
			accountParams.setAccountId(castle.getAccountId());
			accountParams.setCastlePoint(castle.getCastlePoint());
			
			Account account = accountService.get(castle.getAccountId());
			int castleLastClearFloor = account.getCastleLastClearFloor();
			if (castle.getCastleFloor() > castleLastClearFloor) {
				accountParams.setCastleLastClearFloor(castle.getCastleFloor());
			}
			accountService.update(accountParams);
			
			castle.setLastClearLevel(castle.getCastleLevel());
			mapper.save(castle);
		}
		
		return castle;
	}


	@Override
	public Castle getByCastleId(String accountId, int castleId) {
		return mapper.getByCastleId(accountId, castleId);
	}

}
