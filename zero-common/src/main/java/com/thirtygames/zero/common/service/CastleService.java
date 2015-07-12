package com.thirtygames.zero.common.service;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.Castle;

public interface CastleService extends GenericService<Castle, Integer> {

	public Castle clearReward(Castle castle, long rewardGold);

	public Castle getByCastleId(String accountId, int castleId);
	

}